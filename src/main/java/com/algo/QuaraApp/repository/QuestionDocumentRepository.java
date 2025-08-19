package com.algo.QuaraApp.repository;

import com.algo.QuaraApp.Model.QuestionElasticDocument;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.elasticsearch.repository.ReactiveElasticsearchRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.List;

@Repository
public interface QuestionDocumentRepository extends ReactiveElasticsearchRepository<QuestionElasticDocument, String> {
//    Flux<QuestionElasticDocument> findByTitleContainingOrContentContaining(String title, String content);

    @Query("""
{
  "multi_match": {
    "query": "?0",
    "fields": ["title", "content"]
  }
}
""")
    Flux<QuestionElasticDocument> findByTitleContainingOrContentContaining(String query);

}
