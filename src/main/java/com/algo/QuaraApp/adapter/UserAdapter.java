package com.algo.QuaraApp.adapter;

import com.algo.QuaraApp.DTO.UserRequestDto;
import com.algo.QuaraApp.DTO.UserResponseDto;
import com.algo.QuaraApp.Model.User;

public class UserAdapter {

    public static User toEntity(UserRequestDto dto){
        return User.builder()
                .id(dto.getId())
                .userName(dto.getUserName())
                .name(dto.getName())
                .build();
    }

    public static UserResponseDto toDto(User user){
        return UserResponseDto.builder()

                .userName(user.getUserName())
                .name(user.getName())
                .createdAt(user.getCreatedAt())
                .modifiedAt(user.getModifiedAt())
                .id(user.getId())
                .build();
    }


}
