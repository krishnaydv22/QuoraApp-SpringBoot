package com.algo.QuaraApp.services;

import com.algo.QuaraApp.DTO.QuestionRequestDTO;
import com.algo.QuaraApp.DTO.QuestionResponseDTO;
import com.algo.QuaraApp.Model.Question;
import com.algo.QuaraApp.adapter.QuestionAdapter;
import com.algo.QuaraApp.exception.QuestionNotFoundException;
import com.algo.QuaraApp.repository.QuestionRepository;
import com.algo.QuaraApp.utils.CursorUtils;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class QuestionService implements IQuestionService{

    private final QuestionRepository questionRepository;
    @Override
    public Mono<QuestionResponseDTO> createQuestion(QuestionRequestDTO questionRequestDTO) {


        Question question =   QuestionAdapter.toQuestionEntity(questionRequestDTO);

        return questionRepository.save(question)
                .map(QuestionAdapter::toQuestionResponseDto)
                .doOnSuccess(response -> System.out.println("Question created successfully: " + response))
                           .doOnError(error -> System.out.println("Error creating question: " + error));


    }

    @Override
    public Mono<QuestionResponseDTO> getQuestionById(String id){

        return questionRepository.findById(id)
                .switchIfEmpty(Mono.error(new QuestionNotFoundException("Question not found with id: " + id)))
                .map(QuestionAdapter::toQuestionResponseDto);
    }

    @Override
    public Flux<QuestionResponseDTO> getAllQuestions(String cursor, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        if(!CursorUtils.isValidCursor(cursor)) {
            return questionRepository.findTop10ByOrderByCreatedAtAsc()
                    .take(size)
                    .map(QuestionAdapter::toQuestionResponseDto)
                    .doOnError(error -> System.out.println("Error fetching questions: " + error))
                    .doOnComplete(() -> System.out.println("Questions fetched successfully from default"));
        } else {
            LocalDateTime cursorTimeStamp = CursorUtils.parseCursor(cursor);
            return questionRepository.findByCreatedAtGreaterThanOrderByCreatedAtAsc(cursorTimeStamp, pageable)
                    .map(QuestionAdapter::toQuestionResponseDto)
                    .doOnError(error -> System.out.println("Error fetching questions: " + error))
                    .doOnComplete(() -> System.out.println("Questions fetched successfully"));
        }

    }

    @Override
    public Mono<Void> deleteById(String id) {
        return questionRepository.findById(id)
                .switchIfEmpty(Mono.error(new QuestionNotFoundException("Question not found with id: " + id)))
                .flatMap(existing -> questionRepository.deleteById(id));

    }

    @Override
    public Flux<QuestionResponseDTO> searchQuestions(String searchItem, int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        return questionRepository.findByTitleOrContentContainingIgnoreCase(searchItem,pageable)
                .map(QuestionAdapter::toQuestionResponseDto)
                .doOnError(error -> System.out.println("Error searching questions: " + error))
                .doOnComplete(() -> System.out.println("Questions searched successfully"));

    }
}
