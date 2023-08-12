package com.wanted.backend.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(CustomException.class)
    public ApiResponse handleException(CustomException e, HttpServletRequest request) {
        log.error("errorCode : {}, url {}, message: {}", e.getCustomErrorCode(), request.getRequestURI(), e.getMessage());
        return ApiResponse.builder()
                .status(e.getCustomErrorCode())
                .message(e.getMessage())
                .build();
    }
}
