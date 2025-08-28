package com.algo.QuaraApp.consumers;

import com.algo.QuaraApp.Model.User;
import com.algo.QuaraApp.config.KafkaConfig;
import com.algo.QuaraApp.events.FeedGenerationEvent;
import com.algo.QuaraApp.events.UpdateFollowerEvent;
import com.algo.QuaraApp.events.ViewCountEvent;
import com.algo.QuaraApp.repository.QuestionRepository;
import com.algo.QuaraApp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaEventConsumer {

    private final QuestionRepository questionRepository;

    private final UserRepository userRepository;

    private final FeedEventDispatcher eventDispatcher;
    @KafkaListener(
            topics = KafkaConfig.TOPIC_NAME,
            groupId = "view-count-consumer",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void consume(ViewCountEvent viewCountEvent){
        questionRepository.findById(viewCountEvent.getTargetId())
                .flatMap(question -> {
                    System.out.println("Incrementing view count for question: " + question.getId());
                    Integer views  = question.getViews();
                    question.setViews(views == null ? 0 : views + 1);
                    return questionRepository.save(question);

                })
                .subscribe(updatedQuestion -> { // this will invoke flatMap
                    System.out.println("View count incremented for question: " + updatedQuestion.getId());
                }, error -> {
                    System.out.println("Error incrementing view count for question: " + error.getMessage());
                });

    }


    @KafkaListener(
            topics = KafkaConfig.FOLLOW_UPDATE_TOPIC,
            groupId = "follow-update-topic",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void consumeFollowEvent(UpdateFollowerEvent event){

      userRepository.findById(event.getFollowerId())
              .flatMap(follower -> {
                  System.out.println("updating followingCount of follower " + follower.getId());
                  Integer count =  follower.getFollowing_count();

                  follower.setFollowing_count(count == null ? 1 : count + 1);
                 return userRepository.save(follower);

              }).subscribe();

        userRepository.findById(event.getFollowingId())
                .flatMap(following -> {
                    System.out.println("updating follower of following " + following.getId());
                    Integer count =  following.getFollower_count();
                    following.setFollower_count(count == null ? 1 : count + 1);
                    return userRepository.save(following);

                }).subscribe();




    }

    @KafkaListener(topics = KafkaConfig.GENERATE_FEED_TOPIC, groupId = "generate-feed-consumer",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void consumeFeedEvent(FeedGenerationEvent event) {
        System.out.println("Reached in consumer group");
        eventDispatcher.distpatch(event);
    }
}
