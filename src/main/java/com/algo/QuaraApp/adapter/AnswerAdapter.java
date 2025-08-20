package com.algo.QuaraApp.adapter;

import com.algo.QuaraApp.DTO.AnswerRequestDTO;
import com.algo.QuaraApp.DTO.AnswerResponseDTO;
import com.algo.QuaraApp.Model.Answer;

import java.time.LocalDateTime;

public class AnswerAdapter {

    public static Answer toEntity(AnswerRequestDTO dto){
        return Answer.builder()
                .content(dto.getContent())
                .questionId(dto.getQuestionId())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    public static AnswerResponseDTO toDto(Answer answer){

        return AnswerResponseDTO.builder()
                .id(answer.getId())
                .content(answer.getContent())
                .createdAt(answer.getCreatedAt())
                .updatedAt(answer.getUpdatedAt())
                .build();
    }
}
