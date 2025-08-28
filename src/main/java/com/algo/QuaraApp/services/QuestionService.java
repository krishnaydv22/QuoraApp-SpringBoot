package com.algo.QuaraApp.services;

import com.algo.QuaraApp.DTO.QuestionRequestDTO;
import com.algo.QuaraApp.DTO.QuestionResponseDTO;
import com.algo.QuaraApp.Model.Question;
import com.algo.QuaraApp.adapter.QuestionAdapter;
import com.algo.QuaraApp.adapter.UserAdapter;
import com.algo.QuaraApp.events.FeedGenerationEvent;
import com.algo.QuaraApp.events.ViewCountEvent;
import com.algo.QuaraApp.exception.QuestionNotFoundException;
import com.algo.QuaraApp.producers.KafkaEventProducer;
import com.algo.QuaraApp.repository.QuestionRepository;
import com.algo.QuaraApp.repository.UserRepository;
import com.algo.QuaraApp.utils.CursorUtils;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
//@AllArgsConstructor // constructor with all fields.
@RequiredArgsConstructor // constructor only with final or @NonNull fields.
public class QuestionService implements IQuestionService{

    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;
    private final KafkaEventProducer kafkaEventProducer;

    private final QuestionIndexService questionIndexService;

    @Override
    public Mono<QuestionResponseDTO> createQuestion(QuestionRequestDTO questionRequestDTO) {


//        Question question =   QuestionAdapter.toQuestionEntity(questionRequestDTO);




       //dumping question to elastic search
        // First, check if the user exists
        return userRepository.findById(questionRequestDTO.getAuthorId())
                .switchIfEmpty(Mono.error(new IllegalArgumentException(
                        "User with id " + questionRequestDTO.getAuthorId() + " does not exist"
                )))
                .flatMap(user -> {
                    // Convert request DTO to Question entity
                    Question question = QuestionAdapter.toQuestionEntity(questionRequestDTO);

                    // Save question and return response DTO
                    return questionRepository.save(question)
                            .map(savedQuestion -> {
                                // Dump question to Elasticsearch
                                questionIndexService.createQuestionIndex(savedQuestion);
                                return QuestionAdapter.toQuestionResponseDto(savedQuestion);
                            })
                            .doOnSuccess(response -> {
                                System.out.println("Question created successfully: " + response);

                                // Publish feed generation event to Kafka
                                FeedGenerationEvent feedGenerationEvent = new FeedGenerationEvent();
                                feedGenerationEvent.setTargetId(response.getId());
                                feedGenerationEvent.setTargetType("QUESTION");
                                feedGenerationEvent.setCreatedAt(response.getCreatedAt());

                                kafkaEventProducer.publishFeedGenerationEvent(feedGenerationEvent);
                            });
                })
                .doOnError(error -> System.out.println("Error creating question: " + error));

    }

    @Override
    public Mono<QuestionResponseDTO> getQuestionById(String id){

        return questionRepository.findById(id)
                .map(QuestionAdapter::toQuestionResponseDto)
                .doOnError(error -> System.out.println("Error fetching question: " + error))
                .doOnSuccess(response -> {
                    System.out.println("Question fetched successfully: " + response);
                    ViewCountEvent viewCountEvent = new ViewCountEvent(id, "question", LocalDateTime.now());
                    kafkaEventProducer.publishViewCountEvent(viewCountEvent);

                });
    }

    @Override
    public Flux<QuestionResponseDTO> getAllQuestions(String cursor,  int size) {
        Pageable pageable = PageRequest.of(0, size);

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
