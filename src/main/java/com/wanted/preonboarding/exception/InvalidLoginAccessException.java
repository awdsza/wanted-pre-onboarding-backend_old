package com.wanted.preonboarding.exception;

public class InvalidLoginAccessException extends RuntimeException{
    public InvalidLoginAccessException() {
        super();
    }

    public InvalidLoginAccessException(String message) {
        super(message);
    }
}
