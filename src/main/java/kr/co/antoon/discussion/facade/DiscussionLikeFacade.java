package kr.co.antoon.discussion.facade;

import kr.co.antoon.discussion.application.DiscussionLikeService;
import kr.co.antoon.discussion.application.DiscussionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class DiscussionLikeFacade {
    private final DiscussionLikeService likeService;
    private final DiscussionService discussionService;

    @Transactional
    public void saveOrUpdate(Long memberId, Long discussionId) {
        var discussion = discussionService.findById(discussionId);
        var like = likeService.saveOrUpdate(memberId, discussionId);
        discussion.updateLikeCount(like.getStatus());
    }
}
