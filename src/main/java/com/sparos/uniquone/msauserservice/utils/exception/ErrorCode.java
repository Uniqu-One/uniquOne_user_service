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
    INVALID_TOKEN(HttpStatus.OK, "Token is invalid"),

    NOT_EXIST_TOKEN(HttpStatus.OK,"토큰이 존재하지 않습니다."),

    Expired_TOKEN(HttpStatus.OK, "토큰 유효시간이 만료 되었습니다."),
    UNSUPPORTED_TOKEN(HttpStatus.OK,"지원하지 않는 토큰 입니다."),


    EMPTY_PAYLOAD_TOKEN(HttpStatus.OK,"토큰이 비어 있습니다." );

    private HttpStatus status;
    private String message;
}
