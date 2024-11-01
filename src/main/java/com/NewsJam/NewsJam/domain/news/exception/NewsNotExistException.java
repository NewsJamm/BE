package com.NewsJam.NewsJam.domain.news.exception;

import com.NewsJam.NewsJam.global.enums.statuscode.BaseCode;
import com.NewsJam.NewsJam.global.exception.GeneralException;

public class NewsNotExistException extends GeneralException {
    public NewsNotExistException(BaseCode errorStatus) {
        super(errorStatus);
    }
}
