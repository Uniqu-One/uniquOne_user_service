package com.sparos.uniquone.msauserservice.utils.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparos.uniquone.msauserservice.redisconfirm.service.RedisUtil;
import com.sparos.uniquone.msauserservice.users.dto.user.UserJwtDto;
import com.sparos.uniquone.msauserservice.users.dto.user.UserLoginDto;
import com.sparos.uniquone.msauserservice.users.service.user.UserService;
import com.sparos.uniquone.msauserservice.utils.security.jwt.JwtProvider;
import com.sparos.uniquone.msauserservice.utils.security.jwt.JwtToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;

//@RequiredArgsConstructor
@Slf4j
//@RequiredArgsConstructor
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final UserService userService;
    private final ObjectMapper objectMapper;
    private final RedisUtil redisUtil;

    public AuthenticationFilter(AuthenticationManager authenticationManager, UserService userService
            , ObjectMapper objectMapper, RedisUtil redisUtil) {
        super.setAuthenticationManager(authenticationManager);
        this.userService = userService;
        this.objectMapper = objectMapper;
        this.redisUtil = redisUtil;
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
//        try {
//            UserLoginDto creds = new ObjectMapper().readValue(request.getInputStream(), UserLoginDto.class);
//
//            return getAuthenticationManager().authenticate(
//                    new UsernamePasswordAuthenticationToken(
//                            creds.getEmail(),
//                            creds.getPassword()
////                            new ArrayList<>() 권한을 넘겨줘야되나,,?
//                    )
//            );
//        } catch(IOException e) {
//            throw new RuntimeException(e);
//        }

        try {
            UserLoginDto creds = new ObjectMapper().readValue(request.getInputStream(), UserLoginDto.class);
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(creds.getEmail(), creds.getPassword());

            setDetails(request, auth);

            return this.getAuthenticationManager().authenticate(auth);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
//        super.successfulAuthentication(request, response, chain, authResult);
//        String email = ((Users) authResult.getPrincipal()).getEmail();
        String email = authResult.getPrincipal().toString();
        UserJwtDto userDto = userService.findByEmailForJwt(email);

//        log.info("userdto.id {}: ", userDto.getId());

        JwtToken jwtToken = JwtProvider.generateToken(userDto.getId(), email, userDto.getNickname(), userDto.getRole());

        response.addHeader("email", email);
        writeTokenResponse(response, jwtToken);
        //refresh token 저장. 60 * 60 * 24 * 15L = 15일 초로 계산.
        redisUtil.setDataExpire(email, jwtToken.getRefreshToken(), 60 * 60 * 24 * 15L);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {

//        log.error("unsuccessfulAuthentication failed.getLocalizedMessage(): {}", failed.getLocalizedMessage());

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json;charset=UTF-8");
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("code", HttpStatus.UNAUTHORIZED.value());
        body.put("error", failed.getMessage());

        new ObjectMapper().writeValue(response.getOutputStream(), body);
    }

    private void writeTokenResponse(HttpServletResponse response, JwtToken jwtToken) throws IOException {
        response.setContentType("text/html;charset=UTF-8");


        response.addHeader("token", jwtToken.getToken());
        response.addHeader("refresh", jwtToken.getRefreshToken());
        response.addHeader("Access-Control-Expose-Headers", "token, refresh");

        response.setContentType("application/json;charset=UTF-8");

        PrintWriter writer = response.getWriter();
        writer.println(objectMapper.writeValueAsString(jwtToken));
        writer.flush();
    }
}
