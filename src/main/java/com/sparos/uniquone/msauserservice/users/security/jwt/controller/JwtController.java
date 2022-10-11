package com.sparos.uniquone.msauserservice.users.security.jwt.controller;

import com.sparos.uniquone.msauserservice.users.dto.user.UserJwtDto;
import com.sparos.uniquone.msauserservice.users.security.jwt.JwtProvider;
import com.sparos.uniquone.msauserservice.users.security.jwt.JwtToken;
import com.sparos.uniquone.msauserservice.users.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
public class JwtController {

    private final JwtProvider jwtProvider;

    private final UserService userService;

    @GetMapping("/token/expired")
    public String auth(){
        throw new RuntimeException();
    }

    @GetMapping("/token/refresh")
    public String refreshAuth(HttpServletRequest request, HttpServletResponse response){
//        response.addHeader("token", jwtToken.getToken());
//        response.addHeader("refresh", jwtToken.getRefreshToken());
        String token = request.getHeader("reflash");

        if(token != null && jwtProvider.verifyToken(token)){
            String email = jwtProvider.getEmailId(token);
            UserJwtDto userDto = userService.findByEmailForJwt(email);
            //ㅇㅖ외 처리.
//            userService.
            JwtToken newToken = jwtProvider.generateToken(userDto.getId(),userDto.getEmail(), userDto.getRole());

            response.addHeader("token", newToken.getToken());
            response.addHeader("refresh", newToken.getRefreshToken());
            response.setContentType("application/json;charset=UTF-8");

            return "New Token!";
        }
        throw new RuntimeException();
    }

}
