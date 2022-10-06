package com.sparos.uniquone.msauserservice.users.service;

import com.sparos.uniquone.msauserservice.users.dto.signup.RandomNickDto;
import com.sparos.uniquone.msauserservice.users.dto.user.UserCreateDto;
import com.sparos.uniquone.msauserservice.users.dto.user.UserDto;
import com.sparos.uniquone.msauserservice.users.dto.user.UserJwtDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    UserDto createUser(UserCreateDto userDto);

    UserJwtDto findByEmailForJwt(String email);

    boolean existByNickname(String nickname);

    boolean existByEmail(String email);

    RandomNickDto generateNickName();
}
