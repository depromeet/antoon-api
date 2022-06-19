package kr.co.antoon.vote.dto.response;

import kr.co.antoon.vote.domain.Topic;
import kr.co.antoon.vote.domain.Candidate;
import kr.co.antoon.vote.domain.Vote;

import java.time.LocalDateTime;
import java.util.List;

public record TopicResponse(
        Long topicId,
        String tags,
        String title,
        LocalDateTime votingEndtime,
        Integer joinCount,
        List<Candidate> candidates,
        Boolean voteStauts
) {
    public TopicResponse(Topic topic, List<Candidate> candidates, Vote vote) {
        this(
                topic.getId(),
                topic.getTags(),
                topic.getTitle(),
                topic.getTopicVoteTime(),
                topic.getJoinCount(),
                candidates,
                vote.getVoteStatus()
        );
    }
}
