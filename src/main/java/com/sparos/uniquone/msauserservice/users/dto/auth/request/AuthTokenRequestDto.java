package com.sparos.uniquone.msauserservice.users.dto.auth.request;

import lombok.Data;

@Data
public class AuthTokenRequestDto {
    private String email;
    private String refreshToken;
}
