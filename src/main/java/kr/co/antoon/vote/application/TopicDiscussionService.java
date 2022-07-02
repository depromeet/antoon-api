package kr.co.antoon.vote.application;

import kr.co.antoon.error.dto.ErrorMessage;
import kr.co.antoon.error.exception.common.NotExistsException;
import kr.co.antoon.error.exception.oauth.NotValidRoleException;
import kr.co.antoon.vote.domain.TopicDiscussion;
import kr.co.antoon.vote.dto.request.TopicDiscussionUpdateRequest;
import kr.co.antoon.vote.infrastructure.TopicDiscussionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;


@Service
@RequiredArgsConstructor
public class TopicDiscussionService {
    private final TopicDiscussionRepository topicDiscussionRepository;
    private final int SEC = 60, MIN = 60, HOUR = 24, DAY = 30, MONTH = 12;

    @Transactional
    public TopicDiscussion save(Long userId, Long topicId, String content) {
        return topicDiscussionRepository.save(
                TopicDiscussion.builder()
                        .userId(userId)
                        .topicId(topicId)
                        .content(content)
                        .build()
        );
    }

    @Transactional(readOnly = true)
    public String getTime(LocalDateTime time) {
        LocalDateTime now = LocalDateTime.now();
        long diffTime = time.until(now, ChronoUnit.SECONDS);

        if (diffTime < SEC) {
            return diffTime + "초전";
        }
        diffTime = diffTime / SEC;
        if (diffTime < MIN) {
            return diffTime + "분전";
        }
        diffTime = diffTime / MIN;
        if (diffTime < HOUR) {
            return diffTime + "시간전";
        }
        diffTime = diffTime / HOUR;
        if (diffTime < DAY) {
            return diffTime + "일전";
        }
        diffTime = diffTime / DAY;
        if (diffTime < MONTH) {
            return diffTime + "개월전";
        }
        return time.toLocalDate().toString();
    }

    @Transactional(readOnly = true)
    public TopicDiscussion findById(Long discussionId) {
        return topicDiscussionRepository.findById(discussionId)
                .orElseThrow(() -> new NotExistsException(ErrorMessage.NOT_EXISTS_DISCUSSION_ERROR));
    }

    @Transactional
    public TopicDiscussion update(Long discussionId, Long userId, TopicDiscussionUpdateRequest request) {
        var topicDiscussion = topicDiscussionRepository.findById(discussionId)
                .orElseThrow(() -> new NotExistsException(ErrorMessage.NOT_EXISTS_DISCUSSION_ERROR));
        if (!topicDiscussion.getUserId().equals(userId)) {
            throw new NotValidRoleException(ErrorMessage.NOT_VALID_ROLE_ERROR);
        }
        topicDiscussion.update(request.content());
        return topicDiscussion;
    }

    @Transactional
    public void delete(Long discussionId, Long userId) {
        var topicDiscussion = topicDiscussionRepository.findById(discussionId)
                .orElseThrow(() -> new NotExistsException(ErrorMessage.NOT_EXISTS_DISCUSSION_ERROR));
        if (!topicDiscussion.getUserId().equals(userId)) {
            throw new NotValidRoleException(ErrorMessage.NOT_VALID_ROLE_ERROR);
        }
        topicDiscussionRepository.deleteById(discussionId);
    }

    @Transactional(readOnly = true)
    public Page<TopicDiscussion> findByTopicId(Long topicId, Pageable pageable) {
        return topicDiscussionRepository.findByTopicId(topicId, pageable);
    }
}
