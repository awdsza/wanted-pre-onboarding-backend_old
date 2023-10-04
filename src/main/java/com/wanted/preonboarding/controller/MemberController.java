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
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final Encryptor encryptor;


    @PostMapping("/member")
    public ResponseEntity<?> postMember(@Valid @ModelAttribute MemberForm memberForm) {

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
    public ResponseEntity<?> loginMember(@Valid @ModelAttribute MemberForm memberForm, HttpServletRequest request, HttpServletResponse response) {
        try{
            String password = encryptor.encryptMessage(memberForm.getPassword().getBytes(StandardCharsets.UTF_8));
            memberForm.setPassword(password);
            String token = memberService.login(memberForm);
            response.addHeader(HttpHeaders.AUTHORIZATION,"BASIC "+token);
            return new ResponseEntity<>(new ResponseDto<String>("로그인을 성공했습니다",token), HttpStatus.OK);
        }catch(InvalidKeyException | NoSuchPaddingException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException e){
            throw new EncryptException();
        }

    }
    @PostMapping("/logout")
    public ResponseEntity<?> logoutMember(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.invalidate();
        return new ResponseEntity<>(new ResponseDto<>("로그아웃에 성공했습니다.",""), HttpStatus.OK);
    }

}
