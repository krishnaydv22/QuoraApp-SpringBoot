package com.algo.QuaraApp.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "follows")

public class Follow {
    @Id
    private String id;

    private String follower; //user who want to follow

    private String followed; // who is getting followed by follower

    private LocalDateTime createdAt;


}
