package kr.co.antoon.vote.dto.response;

import kr.co.antoon.vote.domain.Topic;
import kr.co.antoon.vote.domain.Candidate;

import java.time.LocalDateTime;
import java.util.List;

public record TopicResponse(
        Long topicId,
        String tags,
        String title,
        LocalDateTime topicVoteEndTime,
        Integer joinCount,
        List<Candidate> candidates,
        boolean topicVoteStatus
) {
    public TopicResponse(Topic topic, List<Candidate> candidates) {
        this(
                topic.getId(),
                topic.getTags(),
                topic.getTitle(),
                topic.getTopicVoteTime(),
                topic.getJoinCount(),
                candidates,
                topic.getTopicVoteStatus()
        );
    }
}
