package com.algo.QuaraApp.adapter;

import com.algo.QuaraApp.DTO.QuestionRequestDTO;
import com.algo.QuaraApp.DTO.QuestionResponseDTO;
import com.algo.QuaraApp.Model.Question;

import java.time.LocalDateTime;

public class QuestionAdapter {

    public static  Question toQuestionEntity(QuestionRequestDTO dto){
        return Question.builder()
                .content(dto.getContent())
                .title(dto.getTitle())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    public static QuestionResponseDTO toQuestionResponseDto(Question entity) {
        return QuestionResponseDTO.builder()
                .id(entity.getId())
                .content(entity.getContent())
                .title(entity.getTitle())
                .createdAt(entity.getCreatedAt())
                .build();
    }

}
