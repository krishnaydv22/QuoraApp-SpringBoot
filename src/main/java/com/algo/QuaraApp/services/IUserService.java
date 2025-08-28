package com.algo.QuaraApp.services;

import com.algo.QuaraApp.DTO.UserRequestDto;
import com.algo.QuaraApp.DTO.UserResponseDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface IUserService {

    public Mono<UserResponseDto> createUser(UserRequestDto user);

    public Mono<UserResponseDto> getUseById(String id);

    public Flux<UserResponseDto> getAllUser();

}
