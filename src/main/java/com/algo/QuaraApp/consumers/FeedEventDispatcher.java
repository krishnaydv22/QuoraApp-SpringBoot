package com.algo.QuaraApp.consumers;

import com.algo.QuaraApp.events.FeedGenerationEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class FeedEventDispatcher {
    private final Map<String, IFeedEventHandler> handlerMap  = new HashMap<>();

    public FeedEventDispatcher(List<IFeedEventHandler> handlers){
        handlers.forEach( h-> handlerMap .put(h.getType().toUpperCase(),h));
    }

    public void distpatch(FeedGenerationEvent event){
        IFeedEventHandler handler =  handlerMap.get(event.getTargetType().toUpperCase());
        if (handler == null) {
            throw new IllegalArgumentException("Unsupported feed type: " + event.getTargetType());
        }

        handler.handle(event)
                .doOnError(e -> System.err.println("Error while handling event: " + e.getMessage()))
                .subscribe(); // subscribe at the boundary;


    }




}
