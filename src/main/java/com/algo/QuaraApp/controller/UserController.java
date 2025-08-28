package com.algo.QuaraApp.controller;

import com.algo.QuaraApp.DTO.UserRequestDto;
import com.algo.QuaraApp.DTO.UserResponseDto;
import com.algo.QuaraApp.services.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;

    @PostMapping
    public ResponseEntity<Mono<UserResponseDto>> createUser(@RequestBody UserRequestDto dto){

        return  ResponseEntity.ok(userService.createUser(dto));

    }


}
