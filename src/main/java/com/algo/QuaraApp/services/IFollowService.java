package com.algo.QuaraApp.services;

import reactor.core.publisher.Mono;

import java.util.List;

public interface IFollowService {

    public Mono<String> followUser(String followerId, String followedId);

    public Mono<String> unFollowUser(String unFollowerId, String followedId);
}
