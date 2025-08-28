package com.algo.QuaraApp.DTO;

import com.algo.QuaraApp.Model.Answer;
import com.algo.QuaraApp.Model.Question;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import reactor.core.publisher.Flux;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FeedItemDTO {

    private Question question;
    private List<Answer> answer;



}
