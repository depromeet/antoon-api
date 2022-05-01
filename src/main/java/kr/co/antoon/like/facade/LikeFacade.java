package kr.co.antoon.like.facade;

import kr.co.antoon.discussion.application.DiscussionService;
import kr.co.antoon.discussion.domain.Discussion;
import kr.co.antoon.like.application.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class LikeFacade {
    private final LikeService likeService;
    private final DiscussionService discussionService;

    @Transactional
    public void saveOrUpdate(Long memberId, Long discussionId) {
        Discussion discussion = discussionService.findById(discussionId);
        likeService.saveOrUpdate(discussion, memberId, discussionId);
    }
}
