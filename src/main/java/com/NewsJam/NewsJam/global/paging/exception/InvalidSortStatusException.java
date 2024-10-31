package com.NewsJam.NewsJam.global.paging.exception;

import com.NewsJam.NewsJam.global.enums.statuscode.ErrorStatus;
import com.NewsJam.NewsJam.global.exception.GeneralException;

public class InvalidSortStatusException extends GeneralException {
    public InvalidSortStatusException() {
        super(ErrorStatus._INVALID_SORT_STATUS);
    }
}
