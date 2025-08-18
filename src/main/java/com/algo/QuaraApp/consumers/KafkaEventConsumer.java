package com.algo.QuaraApp.consumers;

import com.algo.QuaraApp.config.KafkaConfig;
import com.algo.QuaraApp.events.ViewCountEvent;
import com.algo.QuaraApp.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaEventConsumer {

    private final QuestionRepository questionRepository;
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
}
