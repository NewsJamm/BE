package com.NewsJam.NewsJam.global.response;

import com.NewsJam.NewsJam.global.enums.statuscode.BaseCode;
import com.NewsJam.NewsJam.global.enums.statuscode.SuccessStatus;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonPropertyOrder({"isSuccess", "code", "message", "result"})
public class ApiResponse<T> {
    private final Boolean isSuccess;
    private final String code;
    private final String message;
    private T result;

    // 성공한 경우
    public static <T> ApiResponse<T> onSuccess(T result) {
        return new ApiResponse<>(true, SuccessStatus._OK.getCode(), SuccessStatus._OK.getMessage(), result);
    }

    public static <T> ApiResponse<T> of(boolean isSuccess, BaseCode code, T result) {
        return new ApiResponse<>(isSuccess, code.getCode(), code.getMessage(), result);
    }

    // 실패한 경우
    public static <T> ApiResponse<T> onFailure(String code, String message, T data) {
        return new ApiResponse<>(false, code, message, data);
    }
}
