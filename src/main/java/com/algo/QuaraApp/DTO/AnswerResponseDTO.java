package com.algo.QuaraApp.DTO;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnswerResponseDTO {
    private String id;

    private String content;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
