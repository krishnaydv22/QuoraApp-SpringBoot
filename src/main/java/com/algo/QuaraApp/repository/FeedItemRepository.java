package com.algo.QuaraApp.repository;

import com.algo.QuaraApp.Model.FeedItem;
import com.algo.QuaraApp.Model.Question;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Repository
public interface FeedItemRepository extends ReactiveMongoRepository<FeedItem,String> {
    Flux<FeedItem> findByUserIdAndCreatedAtGreaterThanOrderByCreatedAtDesc(String userId, LocalDateTime createdAt, Pageable pageable);
    Mono<FeedItem> findByUserIdAndQuestionId(String userId, String questionId);

    Flux<FeedItem> findTop10ByUserIdOrderByCreatedAtDesc(String userId);




}
