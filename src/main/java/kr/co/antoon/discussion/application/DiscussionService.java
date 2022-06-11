package kr.co.antoon.discussion.application;

import kr.co.antoon.discussion.domain.Discussion;
import kr.co.antoon.discussion.dto.DiscussionCountDto;
import kr.co.antoon.discussion.dto.request.DiscussionUpdateRequest;
import kr.co.antoon.discussion.infrastructure.DiscussionRepository;
import kr.co.antoon.error.dto.ErrorMessage;
import kr.co.antoon.error.exception.common.NotExistsException;
import kr.co.antoon.error.exception.oauth.NotValidRoleException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DiscussionService {
    private final DiscussionRepository discussionRepository;
    private final int SEC = 60, MIN = 60, HOUR = 24, DAY = 30, MONTH = 12;

    @Transactional
    public Discussion save(Long userId, Long webtoonId, String content) {
        return discussionRepository.save(
                Discussion.builder()
                        .userId(userId)
                        .webtoonId(webtoonId)
                        .content(content)
                        .build()
        );
    }

    @Transactional(readOnly = true)
    public Discussion findById(Long discussionId) {
        return discussionRepository.findById(discussionId)
                .orElseThrow(() -> new NotExistsException(ErrorMessage.NOT_EXISTS_DISCUSSION_ERROR));
    }

    @Transactional(readOnly = true)
    public Page<Discussion> findAll(Pageable pageable) {
        return discussionRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Page<Discussion> findByWebtoonId(Long webtoonId, Pageable pageable) {
        return discussionRepository.findByWebtoonId(webtoonId, pageable);
    }

    @Transactional
    public Discussion update(Long discussionId, Long userId, DiscussionUpdateRequest request) {
        var discussion = discussionRepository.findById(discussionId)
                .orElseThrow(() -> new NotExistsException(ErrorMessage.NOT_EXISTS_DISCUSSION_ERROR));
        if(!discussion.getUserId().equals(userId)) {
            throw new NotValidRoleException(ErrorMessage.NOT_VALID_ROLE_ERROR);
        }
        discussion.update(request.content());
        return discussion;
    }

    @Transactional
    public void delete(Long discussionId, Long userId) {
        var discussion = discussionRepository.findById(discussionId)
                .orElseThrow(() -> new NotExistsException(ErrorMessage.NOT_EXISTS_DISCUSSION_ERROR));
        if(!discussion.getUserId().equals(userId)) {
            throw new NotValidRoleException(ErrorMessage.NOT_VALID_ROLE_ERROR);
        }
        discussionRepository.deleteById(discussionId);
    }

    @Transactional(readOnly = true)
    public long count() {
        return discussionRepository.count();
    }

    @Transactional(readOnly = true)
    public List<DiscussionCountDto> discussionCount(LocalDateTime before, LocalDateTime now) {
        return discussionRepository.countAllDiscussion(before, now);
    }

    @Transactional(readOnly = true)
    public long countAllLikes() {
        return discussionRepository.findAll()
                .parallelStream()
                .mapToLong(Discussion::getLikeCount)
                .sum();
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
}