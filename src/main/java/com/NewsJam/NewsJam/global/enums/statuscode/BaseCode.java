package com.NewsJam.NewsJam.global.enums.statuscode;

import org.springframework.http.HttpStatus;

public interface BaseCode {
    String getCode();

    String getMessage();

    HttpStatus getHttpStatus();

    Integer getStatusValue();
}
