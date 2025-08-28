package com.algo.QuaraApp.consumers;

import com.algo.QuaraApp.events.FeedGenerationEvent;
import reactor.core.publisher.Mono;

public interface IFeedEventHandler {

    String getType();   // "QUESTION", "ANSWER"
    Mono<Void> handle(FeedGenerationEvent event);
}
