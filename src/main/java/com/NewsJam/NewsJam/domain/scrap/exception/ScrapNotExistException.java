package com.NewsJam.NewsJam.domain.scrap.exception;

import com.NewsJam.NewsJam.global.enums.statuscode.ErrorStatus;
import com.NewsJam.NewsJam.global.exception.GeneralException;

public class ScrapNotExistException extends GeneralException {
    public ScrapNotExistException() {
        super(ErrorStatus._SCRAP_NOT_EXIST);
    }
}
