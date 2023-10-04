package com.wanted.preonboarding.exception;

import com.wanted.preonboarding.dto.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@RestControllerAdvice
public class ExceptionHandlerAdvice {
    @ExceptionHandler(value= MethodArgumentNotValidException.class)
    public Object methodArgumentNotValidException(MethodArgumentNotValidException e){
        String message = e.getBindingResult()
                .getAllErrors()
                .get(0)
                .getDefaultMessage();
        return new ResponseEntity<>(new ResponseDto<String>(message,null), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(value= IllegalArgumentException.class)
    public Object illegalArgumentException(IllegalArgumentException e){
        return new ResponseEntity<>(new ResponseDto<String>(e.getMessage(),null),HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(value= EncryptException.class)
    public Object encryptException(IllegalArgumentException e){
        return new ResponseEntity<>(new ResponseDto<String>(e.getMessage(),null),HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
