package kr.co.antoon.vote.dto.response;

import kr.co.antoon.vote.domain.Topic;
import kr.co.antoon.vote.domain.Candidate;
import kr.co.antoon.vote.domain.vo.TopicCategory;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public record TopicResponse(
        Long topicId,
        TopicCategory topicCategory,
        Set<String> tags,
        String title,
        LocalDateTime topicVoteEndTime,
        Integer joinCount,
        List<Candidate> candidates,
        boolean topicCloseStatus,
        boolean topicVoteStatus
) {
    public TopicResponse(Topic topic, List<Candidate> candidates) {
        this(
                topic.getId(),
                topic.getTopicCategory(),
                Set.of(topic.getTags().split(",")),
                topic.getTitle(),
                topic.getTopicVoteTime(),
                topic.getJoinCount(),
                candidates,
                topic.isTopicCloseStatus(),
                topic.getTopicVoteStatus()
        );
    }
}
