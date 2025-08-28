package com.algo.QuaraApp.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeedGenerationEvent {

    private String targetId;
    private String targetType;
    private LocalDateTime createdAt;

}
