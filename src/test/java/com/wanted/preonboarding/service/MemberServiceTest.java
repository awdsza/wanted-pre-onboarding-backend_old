package com.wanted.preonboarding.service;

import com.wanted.preonboarding.cipher.Encryptor;
import com.wanted.preonboarding.dto.MemberForm;
import com.wanted.preonboarding.entity.Member;
import com.wanted.preonboarding.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest//부트를 띄운 상태로 테스트
@Transactional
class MemberServiceTest {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private Encryptor encryptor;

    @Test
    public void 멤버등록테스트() throws NoSuchAlgorithmException, BadPaddingException, NoSuchPaddingException, IllegalBlockSizeException, InvalidKeyException {
        String email = "ljy1283@wanted.com";

        String password = encryptor.encryptMessage("test1234".getBytes(StandardCharsets.UTF_8));
        Member newMember = Member.createNewMember(email, password);
        Long saveId = memberRepository.save(newMember);
        Assertions.assertThat(saveId).isEqualTo(1);
    }
    @Test
    public void 로그인테스트() throws NoSuchAlgorithmException, BadPaddingException, NoSuchPaddingException, IllegalBlockSizeException, InvalidKeyException {
        String email = "ljy1283@wanted.com";

        String password = encryptor.encryptMessage("test1234".getBytes(StandardCharsets.UTF_8));

        Member newMember = Member.createNewMember(email, password);
        Long saveId = memberRepository.save(newMember);

        Member member = memberRepository.findMember(email, password);
        Assertions.assertThat(member.getEmail()).isEqualTo(email);
    }

}