package kr.co.antoon.like.application;

import kr.co.antoon.discussion.domain.Discussion;
import kr.co.antoon.error.dto.ErrorMessage;
import kr.co.antoon.error.exception.common.NotExistsException;
import kr.co.antoon.like.domain.Like;
import kr.co.antoon.like.infrastructure.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;

    @Transactional
    public void saveOrUpdate(Discussion discussion, Long memberId, Long discussionId) {
        Like like = likeRepository.findByUserIdAndDiscussionId(memberId, discussionId)
                .map(Like::update)
                .orElse(Like.builder()
                        .userId(memberId)
                        .discussionId(discussionId)
                        .build()
                );
        likeRepository.save(like);
        discussion.updateLikeCount(like.getFlag());
    }

    @Transactional
    public Boolean isUserLike(Long userId, Long discussionId) {
        Like like = likeRepository.findByUserIdAndDiscussionId(userId, discussionId)
                .orElseThrow(() -> new NotExistsException(ErrorMessage.NOT_EXISTS_DISCUSSION_ERROR));
        return like.getFlag();
    }
}
