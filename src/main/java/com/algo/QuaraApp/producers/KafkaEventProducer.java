package com.algo.QuaraApp.producers;

import com.algo.QuaraApp.config.KafkaConfig;
import com.algo.QuaraApp.events.FeedGenerationEvent;
import com.algo.QuaraApp.events.UpdateFollowerEvent;
import com.algo.QuaraApp.events.ViewCountEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaEventProducer {

    private final KafkaTemplate<String , Object> kafkaTemplate;

    public void publishViewCountEvent(ViewCountEvent viewCountEvent){
        kafkaTemplate.send(KafkaConfig.TOPIC_NAME,viewCountEvent.getTargetId(),viewCountEvent)
                .whenComplete((result,err) -> {
                    if(err != null){
                        System.out.println("Error publishing view count event: " + err.getMessage());
                    }
                });
    }

    public void publishUpdateFollowerEvent(UpdateFollowerEvent event){
        kafkaTemplate.send(KafkaConfig.FOLLOW_UPDATE_TOPIC, event.getFollowerId(),event)
                .whenComplete((result,err) -> {
                    if(err != null){
                        System.out.println("Error publishing update event: " + err.getMessage());
                    }
                });
    }

    public void publishFeedGenerationEvent(FeedGenerationEvent event){
        kafkaTemplate.send(KafkaConfig.GENERATE_FEED_TOPIC, event.getTargetType(),event)
                .whenComplete((result,err) -> {
                    System.out.println("produced successfully");
                    if(err != null){
                        System.out.println("Error publishing feed generation event: " + err.getMessage());
                    }
                });;

    }
}
