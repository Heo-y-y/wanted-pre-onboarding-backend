package com.wanted.backend.global.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ApiResponse<?>> handleException(CustomException e, HttpServletRequest request) {
        log.error("errorCode : {}, url {}, message: {}", e.getCustomErrorCode(), request.getRequestURI(), e.getMessage());
        ApiResponse<?> response = ApiResponse.builder()
                .status(e.getCustomErrorCode())
                .message(e.getMessage())
                .build();
        return ResponseEntity.status(e.getCustomErrorCode().getStatus()).body(response);
    }
}

