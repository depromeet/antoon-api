package kr.co.antoon.vote.dto.response;

import kr.co.antoon.vote.domain.Topic;

import java.util.List;
import java.util.Set;

public record TopicChoicesResponse(
    List<TopicChoiceResponse> choiceTopics
) {
    public record TopicChoiceResponse(
        Long topicId,
        Set<String> tags,
        String title,
        Integer joinCount
    ) {
        public TopicChoiceResponse(Topic topic) {
            this (
                    topic.getId(),
                    Set.of(topic.getTags().split(",")),
                    topic.getTitle(),
                    topic.getJoinCount()
            );
        }
    }
}
