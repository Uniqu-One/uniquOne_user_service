package com.sparos.uniquone.msauserservice.users.dto.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserPwDto {
    private String oldpassword;
    private String newpassword;
}
