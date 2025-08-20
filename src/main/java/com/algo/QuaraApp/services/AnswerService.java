package com.algo.QuaraApp.services;

import com.algo.QuaraApp.DTO.AnswerRequestDTO;
import com.algo.QuaraApp.DTO.AnswerResponseDTO;
import com.algo.QuaraApp.adapter.AnswerAdapter;
import com.algo.QuaraApp.adapter.QuestionAdapter;
import com.algo.QuaraApp.repository.AnswerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AnswerService implements IAnswerService{

    private final AnswerRepository answerRepository;
    @Override
    public Mono<AnswerResponseDTO> createAnswer(AnswerRequestDTO answerRequestDTO) {
        return answerRepository.save(AnswerAdapter.toEntity(answerRequestDTO))
                .map(AnswerAdapter::toDto);
    }

    @Override
    public Mono<AnswerResponseDTO> getAnswerById(String id) {
        return answerRepository.findById(id)
                .map(AnswerAdapter::toDto)
                .switchIfEmpty(Mono.error(new RuntimeException("Answer not found with id: " + id)));


    }

    @Override
    public Mono<AnswerResponseDTO> updateAnswer(String id, AnswerRequestDTO answerRequestDTO) {
        return answerRepository.findById(id)
                .flatMap(existingAnswer -> {
                    existingAnswer.setContent(answerRequestDTO.getContent());
                    return answerRepository.save(existingAnswer);
                })
                .map(AnswerAdapter::toDto)
                .switchIfEmpty(Mono.error(new RuntimeException("Answer not found with id: " + id)));
    }

    @Override
    public Mono<Void> deleteAnswer(String id) {
        return answerRepository.findById(id)
                .flatMap(answer -> answerRepository.deleteById(id))
                .switchIfEmpty(Mono.error(new RuntimeException("Answer not found with id: " + id)));
    }

    @Override
    public Flux<AnswerResponseDTO> getAllAnswers() {
        return answerRepository.findAll()
                .map(AnswerAdapter::toDto);
    }

    @Override
    public Flux<AnswerResponseDTO> getAnswersByQuestionId(String questionId) {
        return answerRepository.findByQuestionId(questionId)
                .map(AnswerAdapter::toDto);
    }

    @Override
    public Mono<Long> getAnswerCountByQuestionId(String questionId) {
        return null;
    }

    @Override
    public Flux<AnswerResponseDTO> getAnswersByQuestionIdOrderByCreatedAtDesc(String questionId) {
        return null;
    }

    @Override
    public Flux<AnswerResponseDTO> getAnswersByQuestionIdOrderByCreatedAtAsc(String questionId) {
        return null;
    }
}
