package com.algo.QuaraApp.services;

import com.algo.QuaraApp.DTO.AnswerRequestDTO;
import com.algo.QuaraApp.DTO.AnswerResponseDTO;
import reactor.core.publisher.Mono;

public interface IAnswerService {
    public Mono<AnswerResponseDTO> createAnswer(AnswerRequestDTO answerRequestDTO);

    public Mono<AnswerResponseDTO> getAnswerById(String id);
}
