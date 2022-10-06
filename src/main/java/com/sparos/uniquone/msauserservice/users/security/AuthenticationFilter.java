package com.sparos.uniquone.msauserservice.users.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparos.uniquone.msauserservice.users.domain.Users;
import com.sparos.uniquone.msauserservice.users.dto.user.UserJwtDto;
import com.sparos.uniquone.msauserservice.users.dto.user.UserLoginDto;
import com.sparos.uniquone.msauserservice.users.security.jwt.JwtProvider;
import com.sparos.uniquone.msauserservice.users.security.jwt.JwtToken;
import com.sparos.uniquone.msauserservice.users.service.UserService;
import lombok.RequiredArgsConstructor;
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
import java.util.ArrayList;

//@RequiredArgsConstructor
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final UserService userService;
    private final JwtProvider jwtProvider;
    private final ObjectMapper objectMapper;

    public AuthenticationFilter(AuthenticationManager authenticationManager, UserService userService
            , JwtProvider jwtProvider, ObjectMapper objectMapper) {
        super.setAuthenticationManager(authenticationManager);
        this.userService = userService;
        this.jwtProvider = jwtProvider;
        this.objectMapper = objectMapper;
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

        try{
            UserLoginDto creds = new ObjectMapper().readValue(request.getInputStream(), UserLoginDto.class);
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(creds.getEmail(), creds.getPassword());

            setDetails(request,auth);

            return this.getAuthenticationManager().authenticate(auth);
        }catch(IOException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
//        super.successfulAuthentication(request, response, chain, authResult);
//        String email = ((Users) authResult.getPrincipal()).getEmail();
        String email = authResult.getPrincipal().toString();
        UserJwtDto userDto = userService.findByEmailForJwt(email);
        JwtToken jwtToken = jwtProvider.generateToken(email, userDto.getRole());
        response.addHeader("email", email);
        writeTokenResponse(response,jwtToken);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        super.unsuccessfulAuthentication(request, response, failed);
        response.setContentType("application/json;charset=UTF-8");
    }

    private void writeTokenResponse(HttpServletResponse response, JwtToken jwtToken) throws IOException{
        response.setContentType("text/html;charset=UTF-8");


        response.addHeader("token", jwtToken.getToken());
        response.addHeader("refresh", jwtToken.getRefreshToken());

        response.setContentType("application/json;charset=UTF-8");

        PrintWriter writer = response.getWriter();
        writer.println(objectMapper.writeValueAsString(jwtToken));
        writer.flush();
    }
}
