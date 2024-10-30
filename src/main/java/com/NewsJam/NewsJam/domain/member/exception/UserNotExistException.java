package com.NewsJam.NewsJam.domain.member.exception;

import com.NewsJam.NewsJam.global.enums.statuscode.BaseCode;
import com.NewsJam.NewsJam.global.exception.GeneralException;

public class UserNotExistException extends GeneralException {
    public UserNotExistException(BaseCode errorStatus) {
        super(errorStatus);
    }
}
