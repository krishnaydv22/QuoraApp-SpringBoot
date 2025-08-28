package com.algo.QuaraApp.controller;

import com.algo.QuaraApp.DTO.FeedItemDTO;
import com.algo.QuaraApp.services.FeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/feed")
public class FeedController {

    private final FeedService feedService;

    @GetMapping("/{userId}")
    public Flux<FeedItemDTO> getFeed(@PathVariable String userId,
                                     @RequestParam(defaultValue = "0") String cursor,
                                     @RequestParam(defaultValue = "10") int size

                                     ) {
        return feedService.generateFeedForUser(userId,cursor,size);
    }
}
