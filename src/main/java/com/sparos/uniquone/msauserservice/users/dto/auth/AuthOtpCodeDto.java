package com.sparos.uniquone.msauserservice.users.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthOtpCodeDto {
    @Email
    private String email;
    @NotNull
    private Integer code;
}
