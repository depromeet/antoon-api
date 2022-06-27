package kr.co.antoon.vote.dto.response;

import kr.co.antoon.vote.domain.Topic;
import kr.co.antoon.vote.domain.vo.TopicCategory;

import java.time.LocalDateTime;
import java.util.Set;

public record TopicPageResponse(
        Long topicId,
        TopicCategory topicCategory,
        Set<String> tags,
        String title,
        LocalDateTime topicVoteEndTime,
        Integer joinCount,
        String[] thumbnails
) {
    public TopicPageResponse(Topic topic, String[] thumbnails) {
        this(
                topic.getId(),
                topic.getTopicCategory(),
                Set.of(topic.getTags().split(",")),
                topic.getTitle(),
                topic.getTopicVoteTime(),
                topic.getJoinCount(),
                thumbnails
        );
    }
}
