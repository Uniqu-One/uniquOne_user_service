package com.sparos.uniquone.msauserservice.users.controller;

import com.sparos.uniquone.msauserservice.users.dto.auth.AuthEmailDto;
import com.sparos.uniquone.msauserservice.users.dto.auth.AuthOtpCodeDto;
import com.sparos.uniquone.msauserservice.users.dto.auth.request.AuthSmsRequestDto;
import com.sparos.uniquone.msauserservice.users.dto.signup.ExistNicknameResponseDto;
import com.sparos.uniquone.msauserservice.users.service.auth.AuthService;
import com.sparos.uniquone.msauserservice.users.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor

public class AuthController {
    private final UserService userService;

    private final AuthService authService;

    @GetMapping("/{nickname}/exist")
    public ResponseEntity<ExistNicknameResponseDto> existsByNickname(@PathVariable("nickname") String nickname){
        ExistNicknameResponseDto existNicknameResponseDto = new ExistNicknameResponseDto();
        existNicknameResponseDto.setExistNickName(userService.existByNickname(nickname));
        return ResponseEntity.status(HttpStatus.OK).body(existNicknameResponseDto);
    }

    @PostMapping("/ePush")
    public void sendOtpCodeByEmail(@RequestBody @Validated AuthEmailDto authEmailDto, HttpServletResponse response){

        authService.sendOtpCodeByEmail(authEmailDto.getEmail());

        response.setStatus(HttpServletResponse.SC_OK);
    }

    @PostMapping("/pPush")
    public void sendOtpCodeBySms(@RequestBody @Validated AuthSmsRequestDto authSmsRequestDto, HttpServletResponse response){

        authService.sendOtpCodeBySms(authSmsRequestDto.getEmail(), authSmsRequestDto.getPhoneNum());

    }

    @PostMapping("/check")
    public void optCodeCheck(@RequestBody @Validated AuthOtpCodeDto authOtpCodeDto, HttpServletResponse response){

        boolean isCollect = authService.checkOtpCode(authOtpCodeDto.getEmail(), authOtpCodeDto.getCode());
        if(isCollect){
            response.setStatus(HttpServletResponse.SC_OK);
        }else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }


}
