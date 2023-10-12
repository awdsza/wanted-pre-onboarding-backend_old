package com.wanted.preonboarding.exception;

public class TokenExpiredException extends  RuntimeException{
    public TokenExpiredException() {
        super();
    }

    public TokenExpiredException(String message){
        super(message);
    }
}
