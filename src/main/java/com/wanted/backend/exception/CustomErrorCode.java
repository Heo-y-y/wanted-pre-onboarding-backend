package com.wanted.backend.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum CustomErrorCode {
    CREATED_ID(HttpStatus.CREATED, "회원가입이 완료되었습니다."),
    EXISTS_EMAIL(HttpStatus.BAD_REQUEST, "해당 이메일이 존재합니다."),
    PASSWORD_DO_NOT_MATCH(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다.");

    private final HttpStatus status;
    private final String message;
}
