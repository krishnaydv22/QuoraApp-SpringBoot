package com.algo.QuaraApp.services;

import com.algo.QuaraApp.Model.Follow;
import com.algo.QuaraApp.Model.User;
import com.algo.QuaraApp.events.UpdateFollowerEvent;
import com.algo.QuaraApp.producers.KafkaEventProducer;
import com.algo.QuaraApp.repository.FollowRepository;
import com.algo.QuaraApp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FollowService implements IFollowService{

    private final FollowRepository followRepository;

    private final UserRepository userRepository;

    private final KafkaEventProducer kafkaEventProducer;



    @Override
    public Mono<String> followUser(String followerId, String followedId) {

        if (followerId.equals(followedId)) {
            return Mono.just("❌ You cannot follow yourself!");
        }

        return followRepository.findByFollowerAndFollowed(followerId,followedId)
                .flatMap(existinguser ->
                          Mono.just("⚠ You already follow this user")
                )
                .switchIfEmpty(
                        userRepository.findById(followerId)
                                .flatMap(follower ->
                                                userRepository.findById(followedId)
                                                        .flatMap( followed -> {
                                                            if(follower == null || followed == null){
                                                                return Mono.error(new IllegalArgumentException("❌ One of the users not found"));

                                                            }
                                                            Follow follow = Follow.builder()
                                                                    .follower(follower.getId())
                                                                    .followed(followed.getId())
                                                                    .build();

                                                            // Publish Kafka event
                                                            UpdateFollowerEvent event = new UpdateFollowerEvent(
                                                                    followerId, followedId, "Follow", LocalDateTime.now()
                                                            );
                                                            kafkaEventProducer.publishUpdateFollowerEvent(event);


                                                            return followRepository.save(follow)
                                                                    .thenReturn("✅ Now following " + followed.getUserName());


                                                        })
                                        )
                );


    }

    @Override
    public  Mono<String> unFollowUser(String unFollowerId, String followedId) {
        return null;
    }
}
