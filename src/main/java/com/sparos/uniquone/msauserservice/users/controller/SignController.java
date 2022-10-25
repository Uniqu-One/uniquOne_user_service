package com.sparos.uniquone.msauserservice.users.controller;

import com.sparos.uniquone.msauserservice.users.dto.signup.ExistEmailResponseDto;
import com.sparos.uniquone.msauserservice.users.dto.signup.RandomNickDto;
import com.sparos.uniquone.msauserservice.users.dto.user.UserCreateDto;
import com.sparos.uniquone.msauserservice.users.dto.user.UserDto;
import com.sparos.uniquone.msauserservice.users.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/signup")
@RequiredArgsConstructor
@CrossOrigin( origins = "*" )
public class SignController {

    private final UserService userService;

    @PostMapping()
    public ResponseEntity<UserDto> createUser(@RequestBody @Validated UserCreateDto userCreateDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(userCreateDto));
    }

    @GetMapping("/{email}/exist")
    public ResponseEntity<ExistEmailResponseDto> existByEmail(@PathVariable("email") String email){
        ExistEmailResponseDto existEmailResponseDto = new ExistEmailResponseDto();
        existEmailResponseDto.setExistEmail(userService.existByEmail(email));
        return ResponseEntity.status(HttpStatus.OK).body(existEmailResponseDto);
    }

    @GetMapping("/randNick")
    public ResponseEntity<RandomNickDto> generateNickName(){
        RandomNickDto randomNickDto = userService.generateNickName();
        return ResponseEntity.status(HttpStatus.OK).body(randomNickDto);
    }
}
