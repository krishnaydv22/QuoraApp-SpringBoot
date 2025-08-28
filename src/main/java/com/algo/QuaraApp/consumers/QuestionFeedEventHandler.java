package com.algo.QuaraApp.consumers;

import com.algo.QuaraApp.Model.FeedItem;
import com.algo.QuaraApp.Model.Follow;
import com.algo.QuaraApp.Model.Question;
import com.algo.QuaraApp.Model.User;
import com.algo.QuaraApp.consumers.IFeedEventHandler;
import com.algo.QuaraApp.events.FeedGenerationEvent;
import com.algo.QuaraApp.repository.FeedItemRepository;
import com.algo.QuaraApp.repository.FollowRepository;
import com.algo.QuaraApp.repository.QuestionRepository;
import com.algo.QuaraApp.repository.UserRepository;
import com.algo.QuaraApp.services.FeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionFeedEventHandler implements IFeedEventHandler {

    private final QuestionRepository questionRepository;
    private final FeedItemRepository feedItemRepository;
    private final FollowRepository followRepository;

    @Override
    public String getType() {
        return "QUESTION";
    }

  /*  4️⃣ Rule of Thumb

           Use .map(...) when your function returns a plain object.
            (e.g., Mono<Question> → Mono<FeedItem>)

            Use .flatMap(...) when your function returns a Mono.
            (e.g., Mono<Question> → Mono<FeedItem> after DB save)

            Use .flatMapMany(...) when your function returns a Flux.
            (e.g., Mono<Question> → Flux<User> followers).

   */

    @Override
    public Mono<Void> handle(FeedGenerationEvent event) {
        System.out.println("Question id from feedgenerationEvent " + event.getTargetId());
//        questionRepository.findById(event.getTargetId())
//                .flatMapMany(question ->  // Mono<Question>   -> Flux<Users>
//                        Mono.fromCallable(() -> followRepository.findFollowersByUserId(question.getAuthorId()))
//                                .subscribeOn(Schedulers.boundedElastic()) // run blocking code in boundedElastic thread pool
//                                .flatMapMany(followers -> Flux.fromIterable(followers))
//                            .flatMap( follower -> { //Mono<Follow> -> Mono<FeedItem>
//                                FeedItem feedItem = FeedItem.builder()
//                                        .userId(follower.getId())
//                                        .question(question)
//                                        .answers(List.of())
//                                        .createdAt(question.getCreatedAt())
//                                        .build();
//
//                                System.out.println("inside question service " + feedItem);
//
//                                return feedItemRepository.save(feedItem); // returns Mono<FeedItem>
//
//
//                            })
//
//                ).subscribe(
//                        updatedFeedItemQuestion -> {
//                            System.out.println("updated question for feed: " + updatedFeedItemQuestion.getId());
//                        }, error -> {
//                            System.out.println("Error in updating question feed: " + error.getMessage());
//                        }
//                );

        return questionRepository.findById(event.getTargetId()) // Mono<Question>
                .flatMapMany(question ->
                        followRepository.findByFollowed(String.valueOf(question.getAuthorId()))

                                .flatMap(follower -> {
                                    // construct FeedItem for each follower
                                    FeedItem feedItem = FeedItem.builder()
                                            .userId(follower.getId())
                                            .question(question)
                                            .answers(new ArrayList<>())
                                            .createdAt(question.getCreatedAt())
                                            .build();
                                    return feedItemRepository.save(feedItem)
                                            .doOnNext(fi -> System.out.println("Saved feedItem for user: " + fi.getUserId()));
                                }, 50) // limit concurrency
                )
                .then() // convert Flux<FeedItem> -> Mono<Void>
                .doOnError(e -> {
                    System.err.println("Error saving question feed: " + e.getMessage());
                    e.printStackTrace();
                });


    }
}

