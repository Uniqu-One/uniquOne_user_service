package com.sparos.uniquone.msauserservice.users.controller;

import com.sparos.uniquone.msauserservice.users.dto.user.UserDto;
import com.sparos.uniquone.msauserservice.users.dto.user.UserPwDto;
import com.sparos.uniquone.msauserservice.users.dto.user.UserUpdateNickDto;
import com.sparos.uniquone.msauserservice.users.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/welcome")
    public String welcome(){
        return "welcome user service";
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
