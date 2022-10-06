package com.sparos.uniquone.msauserservice.users.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserJwtDto {
    private String email;
    private String nickname;
    private String role;
}
