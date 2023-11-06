package com.wanted.preonboarding.controller;

import com.wanted.preonboarding.cipher.Encryptor;
import com.wanted.preonboarding.dto.MemberForm;
import com.wanted.preonboarding.dto.ResponseDto;
import com.wanted.preonboarding.exception.EncryptException;
import com.wanted.preonboarding.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@RestController
@RequiredArgsConstructor
//@Tag(name="Member",description = "회원가입,로그인처리 API 목록")
public class MemberController {

    private final MemberService memberService;
    private final Encryptor encryptor;

    @PostMapping(value="/member")
    public ResponseEntity<?> postMember(@Valid @RequestBody MemberForm memberForm) {

        try{
            String password = encryptor.encryptMessage(memberForm.getPassword().getBytes(StandardCharsets.UTF_8));
            memberForm.setPassword(password);
            memberService.save(memberForm);
            return new ResponseEntity<>(new ResponseDto<>("회원가입이 완료되었습니다.",""), HttpStatus.CREATED);
        }catch(InvalidKeyException | NoSuchPaddingException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException e){
            throw new EncryptException();
        }
    }
    @PostMapping("/login")
    public ResponseEntity<?> loginMember(@Valid @RequestBody MemberForm memberForm, HttpServletResponse response) {
        try{
            String password = encryptor.encryptMessage(memberForm.getPassword().getBytes(StandardCharsets.UTF_8));
            memberForm.setPassword(password);
            String token = memberService.login(memberForm);
            response.addHeader(HttpHeaders.AUTHORIZATION,"BASIC "+token);
            return new ResponseEntity<>(new ResponseDto<String>("로그인을 성공했습니다",token), HttpStatus.OK);
        }catch(InvalidKeyException | NoSuchPaddingException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException e){
            throw new EncryptException();
        }catch(EmptyResultDataAccessException nre){
            return new ResponseEntity<>(new ResponseDto<String>("아이디 또는 비밀번호가 틀렸습니다.",""),HttpStatus.OK);
        }

    }
    @PostMapping("/logout")
    public ResponseEntity<?> logoutMember(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.invalidate();
        return new ResponseEntity<>(new ResponseDto<>("로그아웃에 성공했습니다.",""), HttpStatus.OK);
    }

    @ExceptionHandler(value= EncryptException.class)
    public Object encryptException(IllegalArgumentException e){
        return new ResponseEntity<>(new ResponseDto<String>(e.getMessage(),null),HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
