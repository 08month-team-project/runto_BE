package com.runto.domain.gathering.exception;

import com.runto.global.exception.ErrorCode;
import lombok.Getter;

@Getter
public class GatheringException extends RuntimeException {

    private final ErrorCode errorCode;

    public GatheringException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
