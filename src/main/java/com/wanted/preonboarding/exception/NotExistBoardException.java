package com.wanted.preonboarding.exception;

public class NotExistBoardException extends RuntimeException{
    public NotExistBoardException() {
        super();
    }

    public NotExistBoardException(String message) {
        super(message);
    }
}
