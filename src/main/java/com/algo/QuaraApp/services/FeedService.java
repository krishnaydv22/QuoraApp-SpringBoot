package com.algo.QuaraApp.services;

import com.algo.QuaraApp.DTO.AnswerResponseDTO;
import com.algo.QuaraApp.DTO.FeedItemDTO;
import com.algo.QuaraApp.DTO.QuestionResponseDTO;
import com.algo.QuaraApp.Model.User;
import com.algo.QuaraApp.adapter.AnswerAdapter;
import com.algo.QuaraApp.adapter.FeedItemAdapter;
import com.algo.QuaraApp.adapter.QuestionAdapter;
import com.algo.QuaraApp.repository.AnswerRepository;
import com.algo.QuaraApp.repository.FeedItemRepository;
import com.algo.QuaraApp.repository.FollowRepository;
import com.algo.QuaraApp.repository.QuestionRepository;
import com.algo.QuaraApp.utils.CursorUtils;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@RequiredArgsConstructor
@Service
public class FeedService {

    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final FollowRepository followRepository;

    private final FeedItemRepository feedItemRepository;

    public Flux<FeedItemDTO> generateFeedForUser(String id, String createdAt,int size){
        Pageable pageable = PageRequest.of(0, size);

        if(!CursorUtils.isValidCursor(createdAt)){
            return feedItemRepository.findTop10ByUserIdOrderByCreatedAtDesc(id)
                    .take(size)

                    .map(FeedItemAdapter::toDto)
                    .doOnError(error -> System.out.println("Error fetching questions: " + error))
                    .doOnComplete(() -> System.out.println("Questions fetched successfully from default"));

        }else{
            return feedItemRepository.findByUserIdAndCreatedAtGreaterThanOrderByCreatedAtDesc(id,CursorUtils.parseCursor(createdAt),pageable)
                    .map(FeedItemAdapter::toDto)
                    .doOnError(error -> System.out.println("Error fetching feedItem: " + error))
                    .doOnComplete(() -> System.out.println("Feed fetched successfully from cursor"));


        }









    }


}
