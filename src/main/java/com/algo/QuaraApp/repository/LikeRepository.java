package com.algo.QuaraApp.repository;

import com.algo.QuaraApp.Model.Like;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends ReactiveMongoRepository<Like,String> {
}
