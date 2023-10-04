package com.wanted.preonboarding.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResponseDto<T> {
    private String message;
    private T result;

    public ResponseDto(String message, T result) {
        this.message = message;
        this.result = result;
    }
}
