package kr.co.antoon.discussion.application;

import kr.co.antoon.discussion.domain.Discussion;
import kr.co.antoon.discussion.domain.DiscussionLike;
import kr.co.antoon.discussion.infrastructure.DiscussionLikeRepository;
import kr.co.antoon.error.dto.ErrorMessage;
import kr.co.antoon.error.exception.common.NotExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class DiscussionLikeService {
    private final DiscussionLikeRepository likeRepository;

    @Transactional
    public void saveOrUpdate(Discussion discussion, Long memberId, Long discussionId) {
        var like = likeRepository.findByUserIdAndDiscussionId(memberId, discussionId)
                .map(DiscussionLike::update)
                .orElse(DiscussionLike.builder()
                        .userId(memberId)
                        .discussionId(discussionId)
                        .build()
                );
        likeRepository.save(like);
        discussion.updateLikeCount(like.getStatus());
    }

    @Transactional
    public Boolean isUserLike(Long userId, Long discussionId) {
        var like = likeRepository.findByUserIdAndDiscussionId(userId, discussionId)
                .orElseThrow(() -> new NotExistsException(ErrorMessage.NOT_EXISTS_DISCUSSION_ERROR));
        return like.getStatus();
    }
}
