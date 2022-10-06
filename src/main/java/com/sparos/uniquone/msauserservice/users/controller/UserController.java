package com.sparos.uniquone.msauserservice.users.controller;

import com.sparos.uniquone.msauserservice.users.domain.Users;
import com.sparos.uniquone.msauserservice.users.dto.user.UserChatResponseDto;
import com.sparos.uniquone.msauserservice.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;

    @GetMapping("/welcome")
    public String welcome(){
        return "welcome user service";
    }

    // 채팅 - 유저 정보 요청 API
    @GetMapping("/chat/userInfo/{userId}")
    public UserChatResponseDto chatUserInfo(@PathVariable("userId") Long userId){
        System.err.println("냐냐냐냐");
        System.err.println("userId" + userId);

        Users user = userRepository.findById(userId).get();

        return UserChatResponseDto.builder()
                .userId(user.getId())
                .nickname(user.getNickname())
                .build();
    }
}
