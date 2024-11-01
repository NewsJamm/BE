package com.NewsJam.NewsJam.domain.scrap.exception;

import com.NewsJam.NewsJam.global.enums.statuscode.BaseCode;
import com.NewsJam.NewsJam.global.exception.GeneralException;

public class ScrapNotExistException extends GeneralException {
    public ScrapNotExistException(BaseCode errorStatus) {
        super(errorStatus);
    }
}
