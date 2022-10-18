package com.sparos.uniquone.msauserservice.utils.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    DUPLICATED_USER_NAME(HttpStatus.CONFLICT, "유저 이름 중복 입니다."),
    DUPLICATED_USER_NICKNAME(HttpStatus.CONFLICT, "닉네임 중복 입니다."),
    DUPLICATED_USER_EMAIL(HttpStatus.CONFLICT, "이메일 중복 입니다."),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "아이디 또는 비밀번호가 일치하지 않습니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error"),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 유저가 없습니다"),

    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "Token is invalid")
    ;

    private HttpStatus status;
    private String message;
}
