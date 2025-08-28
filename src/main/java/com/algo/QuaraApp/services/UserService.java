package com.algo.QuaraApp.services;

import com.algo.QuaraApp.DTO.UserRequestDto;
import com.algo.QuaraApp.DTO.UserResponseDto;
import com.algo.QuaraApp.Model.User;
import com.algo.QuaraApp.adapter.UserAdapter;
import com.algo.QuaraApp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService{

    private final UserRepository userRepository;


    @Override
    public Mono<UserResponseDto> createUser(UserRequestDto userDto) {
        User userEntity = UserAdapter.toEntity(userDto);
        Instant now = Instant.now();
        userEntity.setCreatedAt(now);
        userEntity.setModifiedAt(now);

        return userRepository.save(userEntity)
                .map(UserAdapter::toDto);

    }








    @Override
    public Mono<UserResponseDto> getUseById(String id) {
        return userRepository.findById(id)
                .map(UserAdapter::toDto);

    }

    @Override
    public Flux<UserResponseDto> getAllUser() {
        return userRepository.findAll()
                .map(UserAdapter::toDto);

    }
}
