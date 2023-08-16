package com.wanted.backend.global.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum CustomErrorCode {
    // 유저
    CREATED_ID(HttpStatus.CREATED, "회원가입이 완료되었습니다."),
    SUCCESS_LOGIN(HttpStatus.OK, "로그인에 성공하였습니다."),
    SUCCESS_LOGOUT(HttpStatus.OK, "로그아웃에 성공했습니다."),
    EXISTS_EMAIL(HttpStatus.BAD_REQUEST, "해당 이메일이 존재합니다."),
    EMAIL_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 이메일은 존재하지 않습니다."),
    PASSWORD_DO_NOT_MATCH(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다."),
    // 토큰
    SUCCESS_REISSUE_TOKEN(HttpStatus.OK, "토큰을 재발급했습니다."),
    ACCESS_TOKEN_INVALID(HttpStatus.UNAUTHORIZED, "유효하지 않은 엑세스 토큰입니다."),
    REFRESH_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "만료된 리프레시 토큰입니다"),
    REFRESH_TOKEN_NOT_FOUND(HttpStatus.UNAUTHORIZED, "존재하지 않는 리프레시 토큰입니다"),
    // 게시판
    CREATE_POST(HttpStatus.CREATED, "게시글이 성공적으로 등록되었습니다.");

    private final HttpStatus status;
    private final String message;
}
