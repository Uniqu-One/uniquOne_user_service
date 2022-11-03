package com.sparos.uniquone.msauserservice.users.controller;

import com.sparos.uniquone.msauserservice.users.domain.Users;
import com.sparos.uniquone.msauserservice.users.dto.user.*;
import com.sparos.uniquone.msauserservice.users.repository.UserRepository;
import com.sparos.uniquone.msauserservice.users.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@RestController
@RequestMapping("/")
public class UserController {
    private final UserRepository userRepository;

    private final UserService userService;

    @GetMapping("/welcome")
    public String welcome(){
        return "welcome Unique-One user service";
    }

    // 채팅 - 유저 정보 요청 API
    @GetMapping("/chat/userInfo/{userId}")
    public UserChatResponseDto chatUserInfo(@PathVariable("userId") Long userId) {

        Users user = userRepository.findById(userId).get();

        return UserChatResponseDto.builder()
                .userId(user.getId())
                .nickname(user.getNickname())
                .build();
    }

    // 후기 - 유저 정보 요청 API
    @GetMapping("/nickname/userInfo/{userId}")
    public String getUserNickName(@PathVariable("userId") Long userId) {
        Users user = userRepository.findById(userId).get();
        return user.getNickname();
    }

    //회원 정보 조회
    @GetMapping
    public ResponseEntity<UserDto> findUserByAuthToken(HttpServletRequest request){
        UserDto userDto = userService.findUserByToken(request);
        return ResponseEntity.status(HttpStatus.OK).body(userDto);
    }

    //회원정보 수정 (닉네임)
    @PatchMapping("/edit")
    public void updateUserNickNameByAuthToken(@RequestBody UserUpdateNickDto userUpdateNickDto, HttpServletRequest request){
        userService.updateUserNicNameByToken(userUpdateNickDto.getNickName(), request);
    }

    @PatchMapping("/pChange")
    public void updateUserPasswordByAuthToken(@RequestBody UserPwDto userPwDto, HttpServletRequest request, HttpServletResponse response){
        boolean isSuccess = userService.updateUserPwByToken(userPwDto, request);
        if(isSuccess){
            response.setStatus(HttpServletResponse.SC_OK);
        }else{
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }



}
