package com.sparos.uniquone.msauserservice.utils.security.oauth2;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparos.uniquone.msauserservice.users.domain.Users;
import com.sparos.uniquone.msauserservice.users.dto.user.UserDto;
import com.sparos.uniquone.msauserservice.users.repository.UserRepository;
import com.sparos.uniquone.msauserservice.utils.security.jwt.JwtProvider;
import com.sparos.uniquone.msauserservice.utils.security.jwt.JwtToken;
import com.sparos.uniquone.msauserservice.users.typeEnum.UserRole;
import com.sparos.uniquone.msauserservice.utils.generate.GenerateRandomNick;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final JwtProvider jwtProvider;
    private final UserRepository usersRepository;

    private final ObjectMapper objectMapper;

    private final GenerateRandomNick generateRandomNick;

//    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        UserDto userDto = oauthToDto(oAuth2User);
        //회원 가입 처리.
        Users users = saveOrUpdate(userDto);

//        log.info("user Id : {}", users.getId());

        JwtToken jwtToken = jwtProvider.generateToken(users.getId(),users.getEmail(), users.getRole().value());

        writeTokenResponse(response,jwtToken);

//        나중에 환경 변수 처리.
//        String url = UriComponentsBuilder.fromUriString("http://10.10.10.138:3000/dk").build().toUriString();
//        String url = UriComponentsBuilder.fromUriString("http://localhost:8000/login").build().toUriString();

//        redirectStrategy.sendRedirect(request,response,url);

    }

    private void writeTokenResponse(HttpServletResponse response, JwtToken jwtToken) throws IOException{
        response.setContentType("text/html;charset=UTF-8");

        response.addHeader("token", jwtToken.getToken());
        response.addHeader("refresh", jwtToken.getRefreshToken());
        response.setContentType("application/json;charset=UTF-8");
//        String url = UriComponentsBuilder.fromUriString("http://localhost:8000/login").build().toUriString();
        String url = UriComponentsBuilder.fromUriString("http://10.10.10.138:3000/dk").build().toUriString();
        response.sendRedirect(url);

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
                        .role(UserRole.ROLES_USER)
                        .build()
                );
        return usersRepository.save(users);
    }

    private UserDto oauthToDto(OAuth2User oAuth2User){
        var attributes = oAuth2User.getAttributes();

        return UserDto.builder()
                .email((String)attributes.get("email"))
                .nickname(generateRandomNick.generate())
                .build();
    }

//    private String makeRedirectUrl(String token) {
//        return UriComponentsBuilder.fromUriString("http://10.10.10.146:3000/" + token)
//                .build().toUriString();
//    }
}
