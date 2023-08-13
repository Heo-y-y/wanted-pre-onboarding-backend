package com.wanted.backend.exception;

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

    public static <T> ApiResponse<T> IdCreated(T data) {
        return new ApiResponse<>(
                CustomErrorCode.CREATED_ID,
                CustomErrorCode.CREATED_ID.getMessage(),
                data
        );
    }
}
