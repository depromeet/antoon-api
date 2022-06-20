package kr.co.antoon.vote.application;

import kr.co.antoon.error.dto.ErrorMessage;
import kr.co.antoon.error.exception.common.NotExistsException;
import kr.co.antoon.vote.domain.Topic;
import kr.co.antoon.vote.infrastructure.TopicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TopicService {
    public static final String LATEST = "latest";
    public static final String RANKS = "ranks";

    private final TopicRepository topicRepository;

    @Transactional(readOnly = true)
    public List<Topic> findAllTopics(String sortType) {
        List<Sort.Order> orders = new ArrayList<>();
        switch (sortType) {
            case LATEST:
                Sort.Order latestOrder = new Sort.Order(Sort.Direction.DESC, "createdAt");
                orders.add(latestOrder);
                break;
            case RANKS:
                Sort.Order topRanksOrder = new Sort.Order(Sort.Direction.DESC, "joinCount");
                orders.add(topRanksOrder);
                break;
            default:
                break;
        }
        return topicRepository.findAll(Sort.by(orders));
    }

    @Transactional(readOnly = true)
    public Topic findById(Long topicId) {
        return topicRepository.findById(topicId)
                .orElseThrow(() -> new NotExistsException(ErrorMessage.NOT_EXIST_TOPIC));
    }
}
