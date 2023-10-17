package com.wanted.preonboarding.repository;

import com.wanted.preonboarding.entity.Member;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {
    private final EntityManager em;
    public Long save(Member member){
        em.persist(member);
        return member.getId();
    }
    public Member findMember(String email,String password) {
        return em.createQuery("SELECT m from Member m where m.email=:email and m.password = :password", Member.class)
                .setParameter("email", email)
                .setParameter("password", password)
                .getSingleResult();
    }
    public List<Member> findMemberByName(String email){
        return em.createQuery("SELECT m from Member m where m.email=:email",Member.class)
                .setParameter("email",email)
                .getResultList();
    }
}
