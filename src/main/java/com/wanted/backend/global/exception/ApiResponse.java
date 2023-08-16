package com.wanted.backend.global.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiResponse<T> {
    private final CustomErrorCode status;
    private final String message;
    private final T data;

    public static <T> ApiResponse<T> idCreated(T data) {
        return new ApiResponse<>(
                CustomErrorCode.CREATED_ID,
                CustomErrorCode.CREATED_ID.getMessage(),
                data
        );
    }

    public static <T> ApiResponse<T> logout() {
        return new ApiResponse<>(
                CustomErrorCode.SUCCESS_LOGOUT,
                CustomErrorCode.SUCCESS_LOGOUT.getMessage(),
                null
        );
    }

    public static <T> ApiResponse<T> reissueToken(T data) {
        return new ApiResponse<>(
                CustomErrorCode.SUCCESS_REISSUE_TOKEN,
                CustomErrorCode.SUCCESS_REISSUE_TOKEN.getMessage(),
                data
        );
    }

    public static <T> ApiResponse<T> login(T data) {
        return new ApiResponse<>(
                CustomErrorCode.SUCCESS_LOGIN,
                CustomErrorCode.SUCCESS_LOGIN.getMessage(),
                data
        );
    }

    public static <T> ApiResponse<T> postCreated(T data) {
        return new ApiResponse<>(
                CustomErrorCode.CREATE_POST,
                CustomErrorCode.CREATE_POST.getMessage(),
                data
        );
    }
}
