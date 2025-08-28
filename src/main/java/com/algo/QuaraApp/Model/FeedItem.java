package com.algo.QuaraApp.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "feed_items")
public class FeedItem {

    @Id
    private String id;

    private String userId; // feed is generated for this user

    private Question question;

    private List<Answer> answers;


    private LocalDateTime createdAt; // for cursor-based pagination


}
