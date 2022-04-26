package kr.co.antoon.discussion.application;

import kr.co.antoon.discussion.domain.Discussion;
import kr.co.antoon.discussion.dto.request.DiscussionUpdateRequest;
import kr.co.antoon.discussion.dto.response.DiscussionReadResponse;
import kr.co.antoon.discussion.dto.response.DiscussionUpdateResponse;
import kr.co.antoon.discussion.infrastructure.DiscussionRepository;
import kr.co.antoon.error.dto.ErrorMessage;
import kr.co.antoon.error.exception.common.NotExistsException;
import kr.co.antoon.like.domain.Like;
import kr.co.antoon.like.infrastructure.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DiscussionService {
    private final DiscussionRepository discussionRepository;
    private final LikeRepository likeRepository;

    @Transactional
    public Discussion save(Long memberId, Long webtoonId, String content) {
        return discussionRepository.save(
                Discussion.builder()
                        .memberId(memberId)
                        .webtoonId(webtoonId)
                        .content(content)
                        .build()
        );
    }

    @Transactional(readOnly = true)
    public DiscussionReadResponse findById(Long memberId, Long discussionId) {
        Discussion discussion = discussionRepository.findById(discussionId)
                .orElseThrow(() -> new NotExistsException(ErrorMessage.NOT_EXISTS_DISCUSSION_ERROR));

        return new DiscussionReadResponse(
                discussion.getId(),
                discussion.getContent(),
                discussion.getMemberId(),
                discussion.getLikeCount(),
                isUserLike(memberId, discussionId)
        );
    }

    @Transactional(readOnly = true)
    public Page<DiscussionReadResponse> findAll(Long memberId, Pageable pageable) {
        return discussionRepository.findAll(pageable)
                .map(discussion -> new DiscussionReadResponse(
                        discussion.getId(),
                        discussion.getContent(),
                        discussion.getMemberId(),
                        discussion.getLikeCount(),
                        isUserLike(memberId, discussion.getId())
                ));
    }

    @Transactional
    public DiscussionUpdateResponse update(Long memberId, Long discussionId, DiscussionUpdateRequest request) {
        Discussion discussion = discussionRepository.findById(discussionId)
                .orElseThrow(() -> new NotExistsException(ErrorMessage.NOT_EXISTS_DISCUSSION_ERROR));

        discussion.update(request.content());

        return new DiscussionUpdateResponse(
                discussion.getId(),
                discussion.getContent(),
                discussion.getMemberId(),
                discussion.getLikeCount(),
                isUserLike(memberId, discussion.getId())
        );
    }

    @Transactional
    public void delete(Long id) {
        discussionRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Boolean isUserLike(Long userId, Long discussionId) {
        Like like = likeRepository.findByUserIdAndDiscussionId(userId, discussionId)
                .orElseThrow(() -> new NotExistsException(ErrorMessage.NOT_EXISTS_DISCUSSION_ERROR));
        return like.getFlag();
    }
}