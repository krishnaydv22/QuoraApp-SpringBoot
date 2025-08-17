package com.algo.QuaraApp.events;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ViewCountEvent {
    private String targetId;
    private String targetType;
    private LocalDateTime timestamp;


}
