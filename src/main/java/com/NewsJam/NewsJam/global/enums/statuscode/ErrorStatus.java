package com.NewsJam.NewsJam.global.enums.statuscode;

import org.springframework.http.HttpStatus;

public enum ErrorStatus implements BaseCode {
    // common
    _INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON500", "서버 에러, 관리자에게 문의 바랍니다."),
    _BAD_REQUEST(HttpStatus.BAD_REQUEST, "COMMON400", "잘못된 요청입니다."),
    _UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "COMMON401", "인증이 필요합니다."),
    _FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON403", "금지된 요청입니다."),
    _PAGE_VARIABLE_INVALID(HttpStatus.BAD_REQUEST, "PAGE4001", "요청한 페이지 혹은 페이지당 요소 개수는 0보다 커야합니다."),
    _INVALID_SORT_STATUS(HttpStatus.BAD_REQUEST, "PAGE4002", "정렬 기준이 올바르지 않습니다."),


    // news
    _NEWS_NOT_EXIST(HttpStatus.NOT_FOUND, "NEWS4001", "존재하지 않는 뉴스입니다."),

    // member
    _MEMBER_NOT_EXIST(HttpStatus.NOT_FOUND, "MEMBER4002", "존재하지 않는 사용자입니다."),
    _EXIST_LOGINID(HttpStatus.NOT_FOUND, "MEMBER4001", "이미 존재하는 사용자 아이디입니다."),

    // scrap
    _SCRAP_NOT_EXIST(HttpStatus.NOT_FOUND, "SCRAP4001", "존재하지 않는 스크랩 정보입니다.");


    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    ErrorStatus(HttpStatus httpStatus, String code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }


    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    @Override
    public Integer getStatusValue() {
        return httpStatus.value();
    }
}
