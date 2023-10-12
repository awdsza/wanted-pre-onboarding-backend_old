package com.wanted.preonboarding.exception;

public class InvalidUserDifferentException extends  RuntimeException{
    public InvalidUserDifferentException() {
        super();
    }

    public InvalidUserDifferentException(String message) {
        super(message);
    }
}
