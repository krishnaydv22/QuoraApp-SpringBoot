package com.algo.QuaraApp.adapter;

import com.algo.QuaraApp.DTO.QuestionElasticDocumentResponseDTO;
import com.algo.QuaraApp.Model.QuestionElasticDocument;

public class QuestionElasticDocumentAdapter {

    public static QuestionElasticDocumentResponseDTO toDto(QuestionElasticDocument document){
        return QuestionElasticDocumentResponseDTO.builder()
                .content(document.getContent())
                .title(document.getTitle())
                .build();
    }
}
