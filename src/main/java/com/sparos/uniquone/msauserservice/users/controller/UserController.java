package com.sparos.uniquone.msauserservice.users.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
@RestController
@RequestMapping("/")
public class UserController {

    private final UserRepository userRepository;

    private final UserService userService;

    @GetMapping("/welcome")
    public String welcome(){
        return "welcome user service";
    }

    // 채팅 - 유저 정보 요청 API
    @GetMapping("/chat/userInfo/{userId}")
    public UserChatResponseDto chatUserInfo(@PathVariable("userId") Long userId){
        System.err.println("냐냐냐냐");
        System.err.println("userId" + userId);
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




        Users user = userRepository.findById(userId).get();

        return UserChatResponseDto.builder()
                .userId(user.getId())
                .nickname(user.getNickname())
                .build();
    }
}
