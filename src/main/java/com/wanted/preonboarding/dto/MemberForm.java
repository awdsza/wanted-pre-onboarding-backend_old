package com.wanted.preonboarding.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberForm {
    @NotNull
    @NotEmpty(message = "이메일 입력은 필수 입니다.")
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9._%+-]+.[A-Za-z]{2,6}$",message = "메일형식이 틀렸습니다.")
    private String email;

    @NotNull
    @NotEmpty(message="비밀번호 입력은 필수 입니다.")
    @Size(min=8,message="비밀번호는 8자이상 입력해야합니다.")
    private String password;
}
