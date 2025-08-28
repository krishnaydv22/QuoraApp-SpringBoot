package com.algo.QuaraApp.repository;

import com.algo.QuaraApp.Model.Follow;
import com.algo.QuaraApp.Model.User;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.query.Param;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends ReactiveMongoRepository<Follow,String> {

    // Get list of Follow documents for users who are following a given user
    Flux<Follow> findByFollowed(String followedUserId);

    // Get list of Follow documents for users that a given user is following
    Flux<Follow> findByFollower(String followerUserId);

    // Check if follow relation exists
    Mono<Follow> findByFollowerAndFollowed(String followerUserId, String followedUserId);

}
