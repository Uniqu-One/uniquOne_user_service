package com.sparos.uniquone.msauserservice.users.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login/oauth2")
public class Oauth2Controller {

    @GetMapping("/kakao")
    public String kakaoOauthRedirect(@RequestParam String code){
        return "카카오 로그인 인증완료, code : " + code;
    }

//    @GetMapping("/naver")
//    public String naverOauthRedirect(@RequestParam String code){
//        return "네이버 로그인 인증완료, code : " + code;
//    }
}
