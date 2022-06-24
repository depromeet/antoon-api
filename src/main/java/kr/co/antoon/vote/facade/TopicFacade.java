package kr.co.antoon.vote.facade;

import kr.co.antoon.vote.application.TopicService;
import kr.co.antoon.vote.application.CandidateService;
import kr.co.antoon.vote.application.VoteService;

import kr.co.antoon.vote.domain.Candidate;
import kr.co.antoon.vote.domain.vo.SortType;
import kr.co.antoon.vote.dto.response.TopicAllResponse;
import kr.co.antoon.vote.dto.response.TopicChoicesResponse;
import kr.co.antoon.vote.dto.response.TopicResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
@RequiredArgsConstructor
public class TopicFacade {
    private final TopicService topicService;
    private final CandidateService candidateService;

    @Transactional(readOnly = true)
    public TopicResponse findTopicById(Long topicId) {
        var topic = topicService.findById(topicId);
        var candidates = candidateService.findAllByTopicId(topicId);
        return new TopicResponse(topic, candidates);
    }

    @Transactional(readOnly = true)
    public TopicAllResponse findAll(SortType sortType) {
        var response = topicService.findAllTopics(sortType)
                .stream()
                .map(topic -> {
                    String[] thumbnails = candidateService.findAllByTopicId(topic.getId())
                            .stream()
                            .map(Candidate::getImageUrl)
                            .toArray(String[]::new);
                    return new TopicAllResponse.TopicResponse(
                            topic,
                            thumbnails
                    );
                }).toList();
        return new TopicAllResponse(response);
    }

    @Transactional(readOnly = true)
    public TopicChoicesResponse getChoiceTopics() {
        var responses = topicService.findAllChoiceTopics()
                .stream()
                .map(TopicChoicesResponse.TopicChoiceResponse::new)
                .toList();
        return new TopicChoicesResponse(responses);
    }
}
