package kr.co.antoon.vote.application;

import kr.co.antoon.error.dto.ErrorMessage;
import kr.co.antoon.error.exception.common.NotExistsException;
import kr.co.antoon.vote.domain.Topic;
import kr.co.antoon.vote.infrastructure.TopicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TopicService {
    private final TopicRepository topicRepository;

    @Transactional(readOnly = true)
    public Topic findById(Long topicId) {
        return topicRepository.findById(topicId)
                .orElseThrow(() -> new NotExistsException(ErrorMessage.NOT_EXIST_TOPIC));
    }
}
