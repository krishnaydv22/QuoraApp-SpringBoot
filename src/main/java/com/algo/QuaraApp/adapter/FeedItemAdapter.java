package com.algo.QuaraApp.adapter;

import com.algo.QuaraApp.DTO.FeedItemDTO;
import com.algo.QuaraApp.Model.FeedItem;

public class FeedItemAdapter {

    public static FeedItemDTO toDto(FeedItem feedItem){
        return FeedItemDTO.builder()
                .question(feedItem.getQuestion())
                .answer(feedItem.getAnswers())
                .build();


    }
}
