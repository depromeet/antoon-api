package kr.co.antoon.vote.facade;

import kr.co.antoon.vote.application.TopicService;
import kr.co.antoon.vote.application.CandidateService;
import kr.co.antoon.vote.application.VoteService;
import kr.co.antoon.vote.domain.Vote;
import kr.co.antoon.vote.dto.response.TopicResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class TopicFacade {
    private final TopicService topicService;
    private final CandidateService candidateService;
    private final VoteService voteService;

    @Transactional(readOnly = true)
    public TopicResponse findTopicById(Long topicId) {
        var topic = topicService.findById(topicId);
        var candidates = candidateService.findAllByTopicId(topicId);
        var vote = voteService.findByTopicId(topicId);
        return new TopicResponse(topic, candidates);
    }
}
