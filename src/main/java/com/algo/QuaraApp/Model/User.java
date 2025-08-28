package com.algo.QuaraApp.Model;

import co.elastic.clients.util.DateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.annotation.Id;


import java.time.Instant;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "users") // MongoDB collection

public class User {

   @Id
    private String id;

    private String name;

    private String userName;

    private Integer follower_count;

    private Integer following_count;

    @Field("created_at")
    private Instant createdAt;

    @Field("updated_at")
    private Instant modifiedAt;



}
