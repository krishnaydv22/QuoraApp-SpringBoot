package com.algo.QuaraApp.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateFollowerEvent {

    private String followerId;
    private String followingId;

    private String targetType;
    private LocalDateTime timestamp;


}
