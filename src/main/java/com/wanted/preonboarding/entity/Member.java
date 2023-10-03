package com.wanted.preonboarding.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="member_id")
    private Long id;
    private String email;
    private String password;

    private Member(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public static Member createNewMember(String email, String password){
        return new Member(email,password);
    }
}
