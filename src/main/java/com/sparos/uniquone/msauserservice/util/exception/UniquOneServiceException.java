package com.sparos.uniquone.msauserservice.util.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UniquOneServiceException extends RuntimeException{

    private ErrorCode errorCode;
    private String message;

    public UniquOneServiceException(ErrorCode errorCode) {
        this.errorCode = errorCode;
        this.message = null;
    }

    @Override
    public String getMessage(){
        if(message == null){
            return errorCode.getMessage();
        }

        return String.format("%s. %s",errorCode.getMessage(), message);
    }

}
