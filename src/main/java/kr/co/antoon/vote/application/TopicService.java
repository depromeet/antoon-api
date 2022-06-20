package kr.co.antoon.vote.application;

import kr.co.antoon.error.dto.ErrorMessage;
import kr.co.antoon.error.exception.common.NotExistsException;
import kr.co.antoon.vote.domain.Topic;
import kr.co.antoon.vote.infrastructure.TopicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TopicService {
    public static final String LATEST = "latest";
    public static final String RANKS = "ranks";
    public static final String CLOSES = "closes";

    private final TopicRepository topicRepository;

    @Transactional(readOnly = true)
    public List<Topic> findAllTopics(String sortType) {
        return switch (sortType) {
            case LATEST -> topicRepository.findAllByOrderByCreatedAtDesc();
            case RANKS -> topicRepository.findAllByOrderByJoinCountDesc();
            case CLOSES -> topicRepository.findAllByTopicVoteTimeLessThan(LocalDateTime.now());
            default -> topicRepository.findAll();
        };
    }

    @Transactional(readOnly = true)
    public Topic findById(Long topicId) {
        return topicRepository.findById(topicId)
                .orElseThrow(() -> new NotExistsException(ErrorMessage.NOT_EXIST_TOPIC));
    }
}
