package com.sparos.uniquone.msauserservice.utils.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
//전체 컨트롤러 결과 값 반환 받기 위해서?
public class Response<T> {

    private String resultCode;
    private T result;

    public static <T> Response<T> success(T result){
        return new Response<>("SUCCESS", result);
    }
    public static <T> Response<T> success(String resultCode,T result){
        return new Response<>(resultCode, result);
    }

    public static <T> Response<T> error(String errorCode){
        return new Response<>(errorCode, null);
    }

    public String toStream() {
        if (result == null) {
            return "{" +
                    "\"resultCode\":" + "\"" + resultCode + "\"," +
                    "\"result\":" + null +
                    "}";
        }
        return "{" +
                "\"resultCode\":" + "\"" + resultCode + "\"," +
                "\"result\":" + "\"" + result + "\"," +
                "}";
    }
}
