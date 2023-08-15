package com.wanted.backend.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
public class LoginDto {
    @Email(message = "올바른 이메일 형식이 아닙니다.")
    @NotBlank(message = "이메일을 입력해주세요")
    private String email;
    @Size(min = 8, message = "비밀번호는 8자 이상이여야 합니다.")
    @NotBlank(message = "비밀번호를 입력해주세요")
    private String password;
}
