package com.sparos.uniquone.msauserservice.users.dto.auth.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class AuthSmsRequestDto {
    private String email;
    private String phoneNum;
}
