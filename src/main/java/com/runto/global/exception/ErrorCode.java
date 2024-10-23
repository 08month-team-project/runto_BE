package com.runto.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    TEMPORARY(null, null);

    private final HttpStatus httpStatus;
    private final String message;
}

