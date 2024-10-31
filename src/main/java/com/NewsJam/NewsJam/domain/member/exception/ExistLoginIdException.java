package com.NewsJam.NewsJam.domain.member.exception;

import com.NewsJam.NewsJam.global.enums.statuscode.BaseCode;
import com.NewsJam.NewsJam.global.exception.GeneralException;

public class ExistLoginIdException extends GeneralException {
    public ExistLoginIdException(BaseCode errorStatus) {
        super(errorStatus);
    }
}