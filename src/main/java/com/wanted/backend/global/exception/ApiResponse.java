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

    public static <T> ApiResponse<T> createId(T data) {
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

    public static <T> ApiResponse<T> createPost(T data) {
        return new ApiResponse<>(
                CustomErrorCode.CREATE_POST,
                CustomErrorCode.CREATE_POST.getMessage(),
                data
        );
    }

    public static <T> ApiResponse<T> updatePost(T data) {
        return new ApiResponse<>(
                CustomErrorCode.UPDATE_POST,
                CustomErrorCode.UPDATE_POST.getMessage(),
                data
        );
    }

    public static <T> ApiResponse<T> deletePost() {
        return new ApiResponse<>(
                CustomErrorCode.DELETE_POST,
                CustomErrorCode.DELETE_POST.getMessage(),
                null
        );
    }

    public static <T> ApiResponse<T> getPost(T data) {
        return new ApiResponse<>(
                CustomErrorCode.SEARCH_POST,
                CustomErrorCode.SEARCH_POST.getMessage(),
                data
        );
    }

    public static <T> ApiResponse<T> getPosts(T data) {
        return new ApiResponse<>(
                CustomErrorCode.SUCCESS_POSTLIST,
                CustomErrorCode.SUCCESS_POSTLIST.getMessage(),
                data
        );
    }
}
