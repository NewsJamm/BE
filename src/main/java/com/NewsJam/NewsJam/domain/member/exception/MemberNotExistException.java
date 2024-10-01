package com.NewsJam.NewsJam.domain.member.exception;

import com.NewsJam.NewsJam.global.enums.statuscode.ErrorStatus;
import com.NewsJam.NewsJam.global.exception.GeneralException;

public class MemberNotExistException extends GeneralException {
    public MemberNotExistException() {
        super(ErrorStatus._MEMBER_NOT_EXIST);
    }
}