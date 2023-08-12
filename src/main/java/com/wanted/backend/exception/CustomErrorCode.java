package com.wanted.backend.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum CustomErrorCode {
    EXISTS_EMAIL(HttpStatus.BAD_REQUEST, "해당 이메일이 존재합니다.");

    private final HttpStatus status;
    private final String message;
}
