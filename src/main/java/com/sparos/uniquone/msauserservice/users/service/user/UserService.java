package com.sparos.uniquone.msauserservice.users.service.user;

import com.sparos.uniquone.msauserservice.users.dto.signup.RandomNickDto;
import com.sparos.uniquone.msauserservice.users.dto.user.UserCreateDto;
import com.sparos.uniquone.msauserservice.users.dto.user.UserDto;
import com.sparos.uniquone.msauserservice.users.dto.user.UserJwtDto;
import com.sparos.uniquone.msauserservice.users.dto.user.UserPwDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.servlet.http.HttpServletRequest;

public interface UserService extends UserDetailsService {

    UserDto createUser(UserCreateDto userDto);
    UserDto findByEmail(String email);

    UserDto findUserByToken(HttpServletRequest request);

    UserDto updateUserNicNameByToken(String nickName, HttpServletRequest request);

    boolean updateUserPwByToken(UserPwDto userPwDto, HttpServletRequest request);

    UserJwtDto findByEmailForJwt(String email);

    boolean existByNickname(String nickname);

    boolean existByEmail(String email);

    RandomNickDto generateNickName();
}
