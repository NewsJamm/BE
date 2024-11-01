package com.NewsJam.NewsJam.global.exception;

import com.NewsJam.NewsJam.global.enums.statuscode.BaseCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public class GeneralException extends RuntimeException {
    private final BaseCode errorStatus;

    public String getErrorCode() {
        return errorStatus.getCode();
    }

    public String getErrorReason() {
        return errorStatus.getMessage();
    }

    public HttpStatus getHttpStatus() {
        return errorStatus.getHttpStatus();
    }
}