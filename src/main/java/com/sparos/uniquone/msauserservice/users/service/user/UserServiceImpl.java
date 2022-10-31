package com.sparos.uniquone.msauserservice.users.service.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparos.uniquone.msauserservice.redisconfirm.service.RedisUtil;
import com.sparos.uniquone.msauserservice.users.dto.auth.request.AuthTokenRequestDto;
import com.sparos.uniquone.msauserservice.users.dto.signup.RandomNickDto;
import com.sparos.uniquone.msauserservice.users.dto.user.UserCreateDto;
import com.sparos.uniquone.msauserservice.users.dto.user.UserDto;
import com.sparos.uniquone.msauserservice.users.domain.Users;
import com.sparos.uniquone.msauserservice.users.dto.user.UserJwtDto;
import com.sparos.uniquone.msauserservice.users.dto.user.UserPwDto;
import com.sparos.uniquone.msauserservice.users.repository.UserRepository;
import com.sparos.uniquone.msauserservice.utils.security.jwt.JwtProvider;
import com.sparos.uniquone.msauserservice.utils.security.jwt.JwtToken;
import com.sparos.uniquone.msauserservice.utils.security.users.CustomUserDetails;
import com.sparos.uniquone.msauserservice.utils.exception.ErrorCode;
import com.sparos.uniquone.msauserservice.utils.exception.UniquOneServiceException;
import com.sparos.uniquone.msauserservice.utils.generate.GenerateRandomNick;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;
import java.util.function.Supplier;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    private final GenerateRandomNick generateRandomNick;

    private final ObjectMapper objectMapper;

    private final RedisUtil redisUtil;

    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//    public CustomUserDetails loadUserByUsername(String username) {
        Supplier<UsernameNotFoundException> s =
                () -> new UsernameNotFoundException(
                        "아이디 또는 패스워드가 틀립니다."
                );
        return userRepository.findByEmail(username).map(CustomUserDetails::new).orElseThrow(s);
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
        return new ModelMapper().map(users, UserDto.class);
    }

    @Override
    public UserDto findUserByToken(HttpServletRequest request) {
        String email = JwtProvider.getUserEmail(request);

        Users user = userRepository.findByEmail(email).get();

        return new ModelMapper().map(user, UserDto.class);
    }

    @Override
    public UserDto updateUserNicNameByToken(String nickName, HttpServletRequest request) {
        String email = JwtProvider.getUserEmail(request);

        Users user = userRepository.findByEmail(email).get();

        user.setNickname(nickName);

        Users saveUser = userRepository.save(user);

        return new ModelMapper().map(saveUser,UserDto.class);
    }

    @Override
    public boolean updateUserPwByToken(UserPwDto userPwDto, HttpServletRequest request) {
        String email = JwtProvider.getUserEmail(request);

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

    @Override
    public String reIssueToken(AuthTokenRequestDto authTokenRequestDto, HttpServletResponse response) throws IOException, ServletException {

        String refreshToken = redisUtil.getDate(authTokenRequestDto.getEmail());

        if(StringUtils.hasText(refreshToken) && authTokenRequestDto.getRefreshToken().equals(refreshToken)){
            Users users = userRepository.findByEmail(authTokenRequestDto.getEmail()).orElseThrow(() -> {
                throw new UniquOneServiceException(ErrorCode.USER_NOT_FOUND);
            });

            JwtToken jwtToken = JwtProvider.generateToken(users.getId(), users.getEmail(), users.getEmail(), users.getRole().value());

            response.addHeader("email", users.getEmail());
            response.setContentType("text/html;charset=UTF-8");
            response.addHeader("token", jwtToken.getToken());
            response.addHeader("refresh", jwtToken.getRefreshToken());
//            writeTokenResponse(response, jwtToken);

            //refresh token 저장. 60 * 60 * 24 * 15L = 15일 초로 계산.
            redisUtil.setDataExpire(users.getEmail(), jwtToken.getRefreshToken(), 60 * 60 * 24 * 15L);

            return "Success reIssue Token";
        }

        return "Fail reIssue Token";
    }

    private void writeTokenResponse(HttpServletResponse response, JwtToken jwtToken) throws IOException {
        response.setContentType("text/html;charset=UTF-8");


        response.addHeader("token", jwtToken.getToken());
        response.addHeader("refresh", jwtToken.getRefreshToken());
        response.addHeader("Access-Control-Expose-Headers", "token, refresh");

        response.setContentType("application/json;charset=UTF-8");

        PrintWriter writer = response.getWriter();
        writer.println(objectMapper.writeValueAsString(jwtToken));
        writer.flush();
    }


}
