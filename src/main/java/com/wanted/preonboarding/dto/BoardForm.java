package com.wanted.preonboarding.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardForm {

    @NotBlank(message = "제목 입력은 필수입니다.")
    private String title;

    private String content;

    @JsonIgnore
    private String author;
}
