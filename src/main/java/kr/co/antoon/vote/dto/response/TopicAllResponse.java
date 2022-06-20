package kr.co.antoon.vote.dto.response;

import kr.co.antoon.vote.domain.Topic;
import kr.co.antoon.vote.domain.vo.TopicCategory;

import java.time.LocalDateTime;
import java.util.List;

public record TopicAllResponse (
        List<TopicResponse> topics
) {
    public record TopicResponse(
            Long topicId,
            TopicCategory topicCategory,
            String tags,
            String title,
            LocalDateTime topicVoteEndTime,
            Integer joinCount,
            String[] thumbnails
    ) {
        public TopicResponse(Topic topic, String[] thumbnails) {
            this(
                    topic.getId(),
                    topic.getTopicCategory(),
                    topic.getTags(),
                    topic.getTitle(),
                    topic.getTopicVoteTime(),
                    topic.getJoinCount(),
                    thumbnails
            );
        }
    }
}
