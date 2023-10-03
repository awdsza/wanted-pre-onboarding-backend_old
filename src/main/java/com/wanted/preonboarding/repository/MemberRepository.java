package com.wanted.preonboarding.repository;

import com.wanted.preonboarding.entity.Member;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberRepository {
    private final EntityManager em;
    public Long save(Member member){
        em.persist(member);
        return member.getId();
    }
}
