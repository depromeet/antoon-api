package kr.co.antoon.vote.dto.response;

import kr.co.antoon.vote.domain.Topic;

import java.util.List;

public record TopicChoicesResponse(
    List<TopicChoiceResponse> choiceTopics
) {
    public record TopicChoiceResponse(
        Long topicId,
        String tags,
        String title,
        Integer joinCount
    ) {
        public TopicChoiceResponse(Topic topic) {
            this (
                    topic.getId(),
                    topic.getTags(),
                    topic.getTitle(),
                    topic.getJoinCount()
            );
        }
    }
}
