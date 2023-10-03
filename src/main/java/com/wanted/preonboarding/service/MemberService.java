package com.wanted.preonboarding.service;

import com.wanted.preonboarding.dto.MemberForm;
import com.wanted.preonboarding.entity.Member;
import com.wanted.preonboarding.repository.MemberRepository;
import com.wanted.preonboarding.util.JWTTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    @Transactional
    public void save(MemberForm memberForm){
        Member newMember = Member.createNewMember(memberForm.getEmail(), memberForm.getPassword());
        memberRepository.save(newMember);
    }
}
