package kr.co.antoon.vote.dto.response;

import kr.co.antoon.vote.domain.Candidate;
import kr.co.antoon.vote.domain.Topic;

import java.time.LocalDateTime;
import java.util.List;

public record TopicAllResponse (
        List<TopicResponse> topics
) {
    public record TopicResponse(
            Long topicId,
            String tags,
            String title,
            LocalDateTime topicVoteEndTime,
            Integer joinCount,
            String[] thumbnails
    ) {
        public TopicResponse(Topic topic, String[] thumbnails) {
            this(
                    topic.getId(),
                    topic.getTags(),
                    topic.getTitle(),
                    topic.getTopicVoteTime(),
                    topic.getJoinCount(),
                    thumbnails
            );
        }
    }
}
