package com.algo.QuaraApp.services;

import com.algo.QuaraApp.DTO.QuestionElasticDocumentResponseDTO;
import com.algo.QuaraApp.Model.Question;
import com.algo.QuaraApp.Model.QuestionElasticDocument;
import com.algo.QuaraApp.adapter.QuestionElasticDocumentAdapter;
import com.algo.QuaraApp.repository.QuestionDocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class QuestionIndexService implements IQuestionIndexService{


    private final QuestionDocumentRepository questionDocumentRepository;
    @Override
    public void createQuestionIndex(Question question) {

        QuestionElasticDocument saved =    QuestionElasticDocument.builder()
                .id(question.getId())
                .title(question.getTitle())
                .content(question.getContent())
                .build();
            questionDocumentRepository.save(saved).subscribe();

    }

    @Override
    public Flux<QuestionElasticDocumentResponseDTO> searchQuestionByElasticSearch(String query) {
        return questionDocumentRepository.findByTitleContainingOrContentContaining(query)
                .map(QuestionElasticDocumentAdapter::toDto);
    }
}
