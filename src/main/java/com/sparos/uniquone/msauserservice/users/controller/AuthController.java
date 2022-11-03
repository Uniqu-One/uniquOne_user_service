package com.sparos.uniquone.msauserservice.users.controller;

import com.sparos.uniquone.msauserservice.users.dto.auth.AuthEmailDto;
import com.sparos.uniquone.msauserservice.users.dto.auth.AuthOtpCodeDto;
import com.sparos.uniquone.msauserservice.users.dto.auth.request.AuthSmsRequestDto;
import com.sparos.uniquone.msauserservice.users.dto.auth.request.AuthTokenRequestDto;
import com.sparos.uniquone.msauserservice.users.dto.signup.ExistNicknameResponseDto;
import com.sparos.uniquone.msauserservice.users.service.auth.AuthService;
import com.sparos.uniquone.msauserservice.users.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final UserService userService;

    private final AuthService authService;


    @PostMapping("/reIssue")
    public ResponseEntity<?> reIssueToken(@RequestBody AuthTokenRequestDto authTokenRequestDto, HttpServletResponse response) throws IOException, ServletException {

        return ResponseEntity.status(HttpStatus.OK).body(userService.reIssueToken(authTokenRequestDto, response));
    }

    @GetMapping("/{nickname}/exist")
    public ResponseEntity<ExistNicknameResponseDto> existsByNickname(@PathVariable("nickname") String nickname) {
        ExistNicknameResponseDto existNicknameResponseDto = new ExistNicknameResponseDto();
        existNicknameResponseDto.setExistNickName(userService.existByNickname(nickname));
        return ResponseEntity.status(HttpStatus.OK).body(existNicknameResponseDto);
    }

    @PostMapping("/pUpdate")
    public void sendUpdateEmailList(@RequestBody @Validated AuthEmailDto authEmailDto, HttpServletRequest request, HttpServletResponse response) throws Exception {
        authService.sendUpdateMailLinkPage(authEmailDto.getEmail(), request, response);
        response.setStatus(HttpServletResponse.SC_OK);
    }

    @PostMapping("/ePush")
    public void sendOtpCodeByEmail(@RequestBody @Validated AuthEmailDto authEmailDto, HttpServletResponse response) {

        authService.sendOtpCodeByEmail(authEmailDto.getEmail());

        response.setStatus(HttpServletResponse.SC_OK);
    }

    @PostMapping("/pPush")
    public void sendOtpCodeBySms(@RequestBody @Validated AuthSmsRequestDto authSmsRequestDto, HttpServletResponse response) {

        authService.sendOtpCodeBySms(authSmsRequestDto.getEmail(), authSmsRequestDto.getPhoneNum());
    }

    @PostMapping("/check")
    public void optCodeCheck(@RequestBody @Validated AuthOtpCodeDto authOtpCodeDto, HttpServletResponse response) {

        boolean isCollect = authService.checkOtpCode(authOtpCodeDto.getEmail(), authOtpCodeDto.getCode());
        if (isCollect) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }


}
