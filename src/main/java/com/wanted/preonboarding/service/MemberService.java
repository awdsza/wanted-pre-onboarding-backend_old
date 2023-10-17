package com.wanted.preonboarding.service;

import com.wanted.preonboarding.dto.MemberForm;
import com.wanted.preonboarding.entity.Member;
import com.wanted.preonboarding.repository.MemberRepository;
import com.wanted.preonboarding.util.JWTTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    private void isDuplicateMember(String email){
        List<Member> members = memberRepository.findMemberByName(email);
        if(!members.isEmpty()){
            throw new IllegalArgumentException("중복된 이메일 입니다");
        }
    }

    @Transactional
    public Long save(MemberForm memberForm){
        isDuplicateMember(memberForm.getEmail());
        Member newMember = Member.createNewMember(memberForm.getEmail(), memberForm.getPassword());
        return memberRepository.save(newMember);
    }

    public String login(MemberForm memberForm){
        Member member = memberRepository.findMember(memberForm.getEmail(), memberForm.getPassword());
        return JWTTokenUtil.createToken("email",member.getEmail());
    }
}
