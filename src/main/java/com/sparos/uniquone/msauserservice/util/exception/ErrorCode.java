package com.sparos.uniquone.msauserservice.util.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    DUPLICATED_USER_NAME(HttpStatus.CONFLICT, "유저 이름 중복 입니다."),
    DUPLICATED_USER_NICKNAME(HttpStatus.CONFLICT, "닉네임 중복 입니다."),
    DUPLICATED_USER_EMAIL(HttpStatus.CONFLICT, "이메일 중복 입니다.");

    private HttpStatus status;
    private String message;
}
