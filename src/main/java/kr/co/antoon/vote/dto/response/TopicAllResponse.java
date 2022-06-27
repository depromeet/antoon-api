package kr.co.antoon.vote.dto.response;

import kr.co.antoon.vote.domain.Topic;
import kr.co.antoon.vote.domain.vo.TopicCategory;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public record TopicAllResponse (
        List<TopicResponse> topics
) {
    public record TopicResponse(
            Long topicId,
            TopicCategory topicCategory,
            Set<String> tags,
            String title,
            LocalDateTime createdAt,
            LocalDateTime topicVoteEndTime,
            Integer joinCount,
            String[] thumbnails
    ) {
        public TopicResponse(Topic topic, String[] thumbnails) {
            this(
                    topic.getId(),
                    topic.getTopicCategory(),
                    Set.of(topic.getTags().split(",")),
                    topic.getTitle(),
                    topic.getCreatedAt(),
                    topic.getTopicVoteTime(),
                    topic.getJoinCount(),
                    thumbnails
            );
        }
    }
}
