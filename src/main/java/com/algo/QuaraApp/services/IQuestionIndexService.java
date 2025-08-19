package com.algo.QuaraApp.services;

import com.algo.QuaraApp.DTO.QuestionElasticDocumentResponseDTO;
import com.algo.QuaraApp.Model.Question;
import com.algo.QuaraApp.Model.QuestionElasticDocument;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IQuestionIndexService {

    void createQuestionIndex(Question question) ;

    Flux<QuestionElasticDocumentResponseDTO> searchQuestionByElasticSearch(String query);


}
