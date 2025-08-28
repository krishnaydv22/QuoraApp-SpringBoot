package com.algo.QuaraApp.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseDto {

    private String id;

    private String name;

    private String userName;

    private Instant createdAt;

    private Instant modifiedAt;


}
