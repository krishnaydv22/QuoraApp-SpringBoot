package com.algo.QuaraApp.consumers;

import com.algo.QuaraApp.Model.FeedItem;
import com.algo.QuaraApp.events.FeedGenerationEvent;
import com.algo.QuaraApp.repository.AnswerRepository;
import com.algo.QuaraApp.repository.FeedItemRepository;
import com.algo.QuaraApp.repository.FollowRepository;
import com.algo.QuaraApp.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AnswerFeedEvent implements IFeedEventHandler{

    private final FeedItemRepository feedItemRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final FollowRepository followRepository;
    @Override
    public String getType() {
        return "ANSWER";
    }

    @Override
    public Mono<Void> handle(FeedGenerationEvent event) {

//        answerRepository.findById(event.getTargetId())
//                .flatMapMany(answer ->
//                                questionRepository.findById(answer.getQuestionId())
//                                        .flatMapMany(question ->
//
//                                                        Flux.fromIterable(followRepository.findFollowersByUserId(question.getAuthorId()))
//                                                                .flatMap( follower ->
//
//                                                                    feedItemRepository.findByUserIdAndQuestionId(follower.getId(),question.getId())
//                                                                            .switchIfEmpty(Mono.fromCallable(() -> FeedItem.builder()
//                                                                                    .userId(follower.getId())
//                                                                                    .question(question)
//                                                                                    .answers(new ArrayList<>(List.of(answer)))
//                                                                                    .createdAt(question.getCreatedAt())
//                                                                                    .build()
//                                                                            ))
//                                                                            .flatMap(feedItem -> {
//                                                                                if (!feedItem.getAnswers().contains(answer)) {
//                                                                                    feedItem.getAnswers().add(answer);
//                                                                                }
//                                                                                return feedItemRepository.save(feedItem);
//                                                                            })
//                                                                )
//
//
//
//                                                )
//
//
//
//                        ).subscribe(
//                        updatedFeedItemAnswer -> { // this will invoke flatMap
//                            System.out.println("updated answer for feed: " + updatedFeedItemAnswer.getId());
//                        }, error -> {
//                            System.out.println("Error in updating answer feed: " + error.getMessage());
//                        }
//                );
//

        return answerRepository.findById(event.getTargetId()) // Mono<Answer>
                .flatMapMany(answer ->
                        questionRepository.findById(answer.getQuestionId())
                                .flatMapMany(question ->
                                        followRepository.findByFollowed(String.valueOf(question.getAuthorId()))
                                                .flatMap(follower ->
                                                        feedItemRepository.findByUserIdAndQuestionId(follower.getId(), question.getId())
                                                                .switchIfEmpty(Mono.fromCallable(() ->
                                                                        FeedItem.builder()
                                                                                .userId(follower.getId())
                                                                                .question(question)
                                                                                .answers(new ArrayList<>(List.of(answer))) // ✅ store as List
                                                                                .createdAt(question.getCreatedAt())
                                                                                .build()
                                                                ))
                                                                .flatMap(feedItem -> {
                                                                    if (!feedItem.getAnswers().contains(answer)) {
                                                                        feedItem.getAnswers().add(answer);
                                                                    }
                                                                    return feedItemRepository.save(feedItem);
                                                                })
                                                )
                                                .subscribeOn(Schedulers.boundedElastic())
                                )
                )
                .then();
    }
}
