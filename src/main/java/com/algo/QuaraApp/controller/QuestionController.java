package com.algo.QuaraApp.controller;

import com.algo.QuaraApp.DTO.QuestionElasticDocumentResponseDTO;
import com.algo.QuaraApp.DTO.QuestionRequestDTO;
import com.algo.QuaraApp.DTO.QuestionResponseDTO;
import com.algo.QuaraApp.Model.QuestionElasticDocument;
import com.algo.QuaraApp.services.IQuestionIndexService;
import com.algo.QuaraApp.services.IQuestionService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/questions")
@AllArgsConstructor
public class QuestionController {

    private final IQuestionService questionService;

    private final IQuestionIndexService questionIndexService;

    @PostMapping
    public Mono<QuestionResponseDTO> createQuestion(@RequestBody QuestionRequestDTO dto){
        return questionService.createQuestion(dto)
                .doOnSuccess(response -> System.out.println("Question created successfully: " + response))
                .doOnError(error -> System.out.println("Error creating question: " + error));

    }

    @GetMapping("/{id}")
    public Mono<QuestionResponseDTO> getQuestionById(@PathVariable String id){
        return questionService.getQuestionById(id);
    }

    @GetMapping()
    public Flux<QuestionResponseDTO> getAllQuestions(   @RequestParam(required = false) String cursor,
//                                                      @RequestParam(defaultValue = "0") int page, not required in cursor based pagination
                                                        //because here we are removing Offset concept
                                                        @RequestParam(defaultValue = "10") int size) {

        return questionService.getAllQuestions(cursor, size)
                .doOnError(error -> System.out.println("Error fetching questions: " + error))
                .doOnComplete(() -> System.out.println("Questions fetched successfully"));

    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteQuestionById(@PathVariable String id) {
        return questionService.deleteById(id);
    }

    @GetMapping("/search")
    public Flux<QuestionResponseDTO> searchQuestions(
            @RequestParam String query,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
       return questionService.searchQuestions(query,page,size);
    }

    @GetMapping("/tag/{tag}")
    public Flux<QuestionResponseDTO> getQuestionsByTag(@PathVariable String tag,
                                                       @RequestParam(defaultValue = "0") int page,
                                                       @RequestParam(defaultValue = "10") int size
    ) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @GetMapping("/elasticsearch")
    public Flux<QuestionElasticDocumentResponseDTO> searchQuestionsByElasticsearch(@RequestParam String query) {
        return
                questionIndexService.searchQuestionByElasticSearch(query)
                .doOnError(error -> System.out.println("Error fetching questions: " + error))
                .doOnComplete(() -> System.out.println("Questions fetched successfully"));

    }
}
