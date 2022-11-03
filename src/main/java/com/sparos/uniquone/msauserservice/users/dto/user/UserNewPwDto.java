package com.sparos.uniquone.msauserservice.users.dto.user;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class UserNewPwDto {
    @NotEmpty
    private String password;
}
