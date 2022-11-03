package com.sparos.uniquone.msauserservice.users.service.user;

import com.sparos.uniquone.msauserservice.users.dto.auth.request.AuthTokenRequestDto;
import com.sparos.uniquone.msauserservice.users.dto.signup.RandomNickDto;
import com.sparos.uniquone.msauserservice.users.dto.user.*;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface UserService extends UserDetailsService {

    UserDto createUser(UserCreateDto userDto);
    UserDto findByEmail(String email);

    UserDto findUserByToken(HttpServletRequest request);

    UserDto updateUserNicNameByToken(String nickName, HttpServletRequest request);

    boolean updateUserPwByToken(UserPwDto userPwDto, HttpServletRequest request);

    boolean updateUserNewPwByToken(UserNewPwDto userPwDto, HttpServletRequest request);

    UserJwtDto findByEmailForJwt(String email);

    boolean existByNickname(String nickname);

    boolean existByEmail(String email);

    RandomNickDto generateNickName();
    String reIssueToken(AuthTokenRequestDto authTokenRequestDto, HttpServletResponse response) throws IOException, ServletException;
}
