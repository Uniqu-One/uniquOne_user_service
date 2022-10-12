package com.sparos.uniquone.msauserservice.utils.exception;

import com.sparos.uniquone.msauserservice.utils.controller.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalControllerAdvice {
    @ExceptionHandler(UniquOneServiceException.class)
    public ResponseEntity<?> applicationHandler(UniquOneServiceException e){
        log.error("Error : {}", e.toString());
        return ResponseEntity.status(e.getErrorCode().getStatus())
                .body(Response.error(e.getErrorCode().getMessage()));
    }
}
