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
import java.util.List;

@Service
@RequiredArgsConstructor
public class DiscussionService {
    private final DiscussionRepository discussionRepository;

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
}