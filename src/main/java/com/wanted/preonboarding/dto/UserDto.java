package com.wanted.preonboarding.dto;

import lombok.Getter;

@Getter
public class UserDto {
    private String email;

    private UserDto(String email) {
        this.email = email;
    }
    public static UserDto createUserDto(String email){
        return new UserDto(email);
    }
}
