package com.runto.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    IMAGE_SAVE_LIMIT_EXCEEDED(BAD_REQUEST, "이미지 저장 허용 개수를 넘었습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}

