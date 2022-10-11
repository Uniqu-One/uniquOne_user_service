package com.sparos.uniquone.msauserservice.users.service.user;

import com.sparos.uniquone.msauserservice.users.dto.signup.RandomNickDto;
import com.sparos.uniquone.msauserservice.users.dto.user.UserCreateDto;
import com.sparos.uniquone.msauserservice.users.dto.user.UserDto;
import com.sparos.uniquone.msauserservice.users.domain.Users;
import com.sparos.uniquone.msauserservice.users.dto.user.UserJwtDto;
import com.sparos.uniquone.msauserservice.users.dto.user.UserPwDto;
import com.sparos.uniquone.msauserservice.users.repository.UserRepository;
import com.sparos.uniquone.msauserservice.users.security.jwt.JwtProvider;
import com.sparos.uniquone.msauserservice.users.security.users.CustomUserDetails;
import com.sparos.uniquone.msauserservice.util.exception.ErrorCode;
import com.sparos.uniquone.msauserservice.util.exception.UniquOneServiceException;
import com.sparos.uniquone.msauserservice.util.generate.GenerateRandomNick;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.math.raw.Mod;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.function.Supplier;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final JwtProvider jwtProvider;

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    private final GenerateRandomNick generateRandomNick;

    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Supplier<UsernameNotFoundException> s =
                () -> new UsernameNotFoundException(
                        "인증 문제! 해당 Email 없음."
                );

        Users users = userRepository.findByEmail(username)
                .orElseThrow(s);
        return new CustomUserDetails(users);
    }

    @Override
    public UserDto createUser(UserCreateDto userDto) {

        userRepository.findByEmail(userDto.getEmail()).ifPresent(it ->{
            throw new UniquOneServiceException(ErrorCode.DUPLICATED_USER_EMAIL, String.format("%s 는 중복 입니다.",userDto.getEmail()));
        });

       userRepository.findByNickname(userDto.getNickname()).ifPresent(it ->{
           throw new UniquOneServiceException(ErrorCode.DUPLICATED_USER_NICKNAME, String.format("%s는 중복입니다.",userDto.getNickname()));
       });

//       userRepository.existsByNickname(userDto.getNickname())

        Users user = Users.builder()
                .email(userDto.getEmail())
                .nickname(userDto.getNickname())
                .pwd(passwordEncoder.encode(userDto.getPassword()))
                .build();

        Users saveUser = userRepository.save(user);
        return new ModelMapper().map(saveUser, UserDto.class);
    }

    @Override
    public UserDto findByEmail(String email) {
        Optional<Users> users = userRepository.findByEmail(email);
//        jwtProvider.getEmailId()
        return new ModelMapper().map(users, UserDto.class);
    }

    @Override
    public UserDto findUserByToken(HttpServletRequest request) {
        String email = jwtProvider.getEmailId(request);

        Users user = userRepository.findByEmail(email).get();

        return new ModelMapper().map(user, UserDto.class);
    }

    @Override
    public UserDto updateUserNicNameByToken(String nickName, HttpServletRequest request) {
        String email = jwtProvider.getEmailId(request);

        Users user = userRepository.findByEmail(email).get();

        user.setNickname(nickName);

        Users saveUser = userRepository.save(user);

        return new ModelMapper().map(saveUser,UserDto.class);
    }

    @Override
    public boolean updateUserPwByToken(UserPwDto userPwDto, HttpServletRequest request) {
        String email = jwtProvider.getEmailId(request);

        Users user = userRepository.findByEmail(email).get();

        //기존 비밀 번호 비교 맞으면 변경
        if(passwordEncoder.matches(userPwDto.getOldpassword(),user.getPwd())){
            user.setPwd(passwordEncoder.encode(userPwDto.getNewpassword()));
            return true;
        }
        // 틀리면 false
        return false;
    }

    //나중에 프론트에 권한 보여줘도 상관 없으면 그냥 통일.
    @Override
    public UserJwtDto findByEmailForJwt(String email) {
        Optional<UserJwtDto> userJwtDto = userRepository.findByEmail(email)
                .map(users -> UserJwtDto.builder()
                        .id((users.getId()))
                        .email(users.getEmail())
                        .nickname(users.getNickname())
                        .role(users.getRole().value())
                        .build());

        return userJwtDto.get();
    }

    @Override
    public boolean existByNickname(String nickname) {
        return userRepository.existsByNickname(nickname);
    }

    @Override
    public boolean existByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public RandomNickDto generateNickName() {
        return new RandomNickDto(generateRandomNick.generate());
    }


}
