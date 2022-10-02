package com.sparos.uniquone.msauserservice.users.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateDto {
    @NotBlank(message = "이메일 비어 있습니다.")
    @Email
    private String email;
    @NotBlank(message = "비밀번호 비어 있습니다.")
    @Size(min=8,max=20, message = "비밀번호는 8 ~ 20자리 이여야 합니다.")
    private String password;
    @NotBlank(message = "별명이 비어 있습니다.")
    @Size(min=5,max=20, message = "별명은 5 ~ 20자리 이여야 합니다.")
    private String nickname;
}
