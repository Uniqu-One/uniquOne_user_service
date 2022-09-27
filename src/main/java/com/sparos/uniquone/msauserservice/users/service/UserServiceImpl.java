package com.sparos.uniquone.msauserservice.users.service;

import com.sparos.uniquone.msauserservice.users.dto.UserDto;
import com.sparos.uniquone.msauserservice.users.entity.Users;
import com.sparos.uniquone.msauserservice.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDto createUser(UserDto userDto) {

        //dto -> entity
        ModelMapper mapper = new ModelMapper();
        //규칙 : 엄격히
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        Users userEntity = mapper.map(userDto, Users.class);

        userRepository.save(userEntity);


        return null;
    }
}
