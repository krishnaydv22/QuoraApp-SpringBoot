package com.algo.QuaraApp.repository;

import com.algo.QuaraApp.Model.Answer;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerRepository extends ReactiveMongoRepository<Answer,String> {
}

