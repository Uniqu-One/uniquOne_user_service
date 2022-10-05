package com.sparos.uniquone.msauserservice.users.service;

import com.sparos.uniquone.msauserservice.users.dto.user.UserCreateDto;
import com.sparos.uniquone.msauserservice.users.dto.user.UserDto;
import com.sparos.uniquone.msauserservice.users.domain.Users;
import com.sparos.uniquone.msauserservice.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDto createUser(UserCreateDto userDto) {

        Users user = Users.builder()
                .email(userDto.getEmail())
                .nickname(userDto.getNickname())
                .pwd(passwordEncoder.encode(userDto.getPassword()))
                .build();

        Users saveUser = userRepository.save(user);
        return new ModelMapper().map(saveUser, UserDto.class);
    }

    @Override
    public boolean existByNickname(String nickname) {
        return userRepository.existsByNickname(nickname);
    }

    @Override
    public boolean existByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

}
