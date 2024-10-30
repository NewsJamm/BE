package com.NewsJam.NewsJam.domain.member.exception;

import com.NewsJam.NewsJam.global.enums.statuscode.ErrorStatus;
import com.NewsJam.NewsJam.global.exception.GeneralException;

public class ExistLoginIdException extends GeneralException {
    public ExistLoginIdException() {
        super(ErrorStatus._EXIST_LOGINID);
    }
}