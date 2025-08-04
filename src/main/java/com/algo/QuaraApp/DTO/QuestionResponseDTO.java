package com.algo.QuaraApp.DTO;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class QuestionResponseDTO {

    private String id;

    private String title;

    private String content;

    private LocalDateTime createdAt;
}
