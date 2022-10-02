package com.sparos.uniquone.msauserservice.users.controller;

import com.sparos.uniquone.msauserservice.users.dto.signup.ExistNicknameResponseDto;
import com.sparos.uniquone.msauserservice.users.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @GetMapping("/{nickname}/nexist")
    public ResponseEntity<ExistNicknameResponseDto> existsByNickname(@PathVariable("nickname") String nickname){
        ExistNicknameResponseDto existNicknameResponseDto = new ExistNicknameResponseDto();
        existNicknameResponseDto.setExistNickName(userService.existByNickname(nickname));
        return ResponseEntity.status(HttpStatus.OK).body(existNicknameResponseDto);
    }

}
