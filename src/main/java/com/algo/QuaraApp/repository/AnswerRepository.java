package com.algo.QuaraApp.repository;

import com.algo.QuaraApp.Model.Answer;
import com.algo.QuaraApp.Model.Question;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.List;

@Repository
public interface AnswerRepository extends ReactiveMongoRepository<Answer,String> {

    Flux<Answer> findByQuestionId(String questionId);

    Flux<Answer>  findByAuthorId(Integer id);


    Flux<Answer> findByQuestionIdIn(List<Integer> questionIds); // to avoid n + 1 query problem (batch processing )
    // SELECT * FROM answers WHERE question_id IN (...);



}

