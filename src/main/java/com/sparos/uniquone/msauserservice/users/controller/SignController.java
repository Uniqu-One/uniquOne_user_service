package com.sparos.uniquone.msauserservice.users.controller;

import com.sparos.uniquone.msauserservice.users.dto.signup.ExistEmailResponseDto;
import com.sparos.uniquone.msauserservice.users.dto.user.UserCreateDto;
import com.sparos.uniquone.msauserservice.users.dto.user.UserDto;
import com.sparos.uniquone.msauserservice.users.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/signup")
@RequiredArgsConstructor
public class SignController {

    private final UserService userService;

    @PostMapping()
    public ResponseEntity<UserDto> createUser(@RequestBody @Validated UserCreateDto userCreateDto){
        UserDto userDto = userService.createUser(userCreateDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(userDto);
    }

    @GetMapping("/{email}/exist")
    public ResponseEntity<ExistEmailResponseDto> existByEmail(@PathVariable("email") String email){
        ExistEmailResponseDto existEmailResponseDto = new ExistEmailResponseDto();
        existEmailResponseDto.setExistEmail(userService.existByEmail(email));
        return ResponseEntity.status(HttpStatus.OK).body(existEmailResponseDto);
    }
}
