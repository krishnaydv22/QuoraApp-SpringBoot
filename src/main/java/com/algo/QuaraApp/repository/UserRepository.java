package com.algo.QuaraApp.repository;

import com.algo.QuaraApp.Model.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface UserRepository extends ReactiveMongoRepository<User,String> {
}
