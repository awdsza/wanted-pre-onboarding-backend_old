package com.wanted.preonboarding.exception;

public class EncryptException extends RuntimeException{
    public EncryptException(String message) {
        super(message);
    }

    public EncryptException() {
        super();
    }
}
