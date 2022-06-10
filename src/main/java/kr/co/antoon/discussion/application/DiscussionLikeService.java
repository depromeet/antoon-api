package kr.co.antoon.discussion.application;

import kr.co.antoon.discussion.domain.DiscussionLike;
import kr.co.antoon.discussion.infrastructure.DiscussionLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DiscussionLikeService {
    private final DiscussionLikeRepository likeRepository;

    @Transactional
    public DiscussionLike saveOrUpdate(Long memberId, Long discussionId) {
        return likeRepository.save(
                likeRepository.findByUserIdAndDiscussionId(memberId, discussionId)
                        .map(DiscussionLike::update)
                        .orElse(DiscussionLike.builder()
                                .userId(memberId)
                                .discussionId(discussionId)
                                .build()
                        )
        );
    }

    @Transactional(readOnly = true)
    public Boolean isUserLike(Long userId, Long discussionId) {
        var like = likeRepository.findByUserIdAndDiscussionId(userId, discussionId);
        if (like.isPresent()) {
            return like.get().getStatus();
        }
        return false;
    }
}