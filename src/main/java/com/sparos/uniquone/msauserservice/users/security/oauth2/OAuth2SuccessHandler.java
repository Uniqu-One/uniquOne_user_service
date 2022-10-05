package com.sparos.uniquone.msauserservice.users.security.oauth2;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparos.uniquone.msauserservice.users.domain.Users;
import com.sparos.uniquone.msauserservice.users.dto.user.UserDto;
import com.sparos.uniquone.msauserservice.users.repository.UserRepository;
import com.sparos.uniquone.msauserservice.users.security.jwt.JwtProvider;
import com.sparos.uniquone.msauserservice.users.security.jwt.JwtToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final JwtProvider jwtProvider;
    private final UserRepository usersRepository;

    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        UserDto userDto = oauthToDto(oAuth2User);
        //회원 가입 처리.
        Users users = saveOrUpdate(userDto);

        JwtToken jwtToken = jwtProvider.generateToken(authentication, users.getEmail());

        writeTokenResponse(response,jwtToken);
    }

    private void writeTokenResponse(HttpServletResponse response, JwtToken jwtToken) throws IOException{
        response.setContentType("text/html;charset=UTF-8");

        response.addHeader("token", jwtToken.getToken());
        response.addHeader("reflash", jwtToken.getRefreshToken());
        response.setContentType("application/json;charset=UTF-8");

        PrintWriter writer = response.getWriter();
        writer.println(objectMapper.writeValueAsString(jwtToken));
        writer.flush();
    }

    private Users saveOrUpdate(UserDto userDto){
        Users users = usersRepository.findByEmail(userDto.getEmail())
//                .map(entity -> entity.setNickname(userDto.getNickname()))
                .orElse(Users.builder()
                        .email(userDto.getEmail())
                        .nickname(userDto.getNickname())
                        .build()
                );
        return usersRepository.save(users);
    }

    private UserDto oauthToDto(OAuth2User oAuth2User){
        var attributes = oAuth2User.getAttributes();
        return UserDto.builder()
                .email((String)attributes.get("email"))
                .nickname(UUID.randomUUID().toString())
                .build();
    }
}
