package com.algo.QuaraApp.controller;

import com.algo.QuaraApp.services.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;

     @PostMapping("/{followerId}/follow/{followedId}")
     public ResponseEntity<Mono<String>> followUser(@PathVariable String followerId, @PathVariable String followedId){
        return ResponseEntity.ok(followService.followUser(followerId,followedId));


     }
}
