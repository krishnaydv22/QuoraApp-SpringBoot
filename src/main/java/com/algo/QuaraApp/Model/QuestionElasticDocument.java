package com.algo.QuaraApp.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Data
@Document(indexName = "questions")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionElasticDocument {

    @Id
    private String id;

    private String title;

    private String content;
}
