package com.sparos.uniquone.msauserservice.users.service;

import com.sparos.uniquone.msauserservice.users.dto.user.UserCreateDto;
import com.sparos.uniquone.msauserservice.users.dto.user.UserDto;

public interface UserService {

    UserDto createUser(UserCreateDto userDto);

    boolean existByNickname(String nickname);

    boolean existByEmail(String email);
}
