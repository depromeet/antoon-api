package kr.co.antoon.vote.facade;

import kr.co.antoon.vote.application.TopicService;
import kr.co.antoon.vote.application.CandidateService;
import kr.co.antoon.vote.application.VoteService;

import kr.co.antoon.vote.dto.response.TopicAllResponse;
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

    @Transactional(readOnly = true)
    public TopicAllResponse findAll(String sortType) {
        var response = topicService.findAllTopics(sortType)
                .stream()
                .map(topic -> {
                    String[] thumbnails = candidateService.findAllByTopicId(topic.getId())
                            .stream()
                            .map(c -> c.getImageUrl())
                            .toArray(String[]::new);
                    return new TopicAllResponse.TopicResponse(
                            topic,
                            thumbnails
                    );
                }).toList();
        return new TopicAllResponse(response);
    }
}
