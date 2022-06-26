package kr.co.antoon.character.facade;

import kr.co.antoon.character.application.CharacterDiscussionLikeService;
import kr.co.antoon.character.application.CharacterDiscussionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class CharacterDiscussionLikeFacade {
    private final CharacterDiscussionLikeService characterDiscussionLikeService;
    private final CharacterDiscussionService characterDiscussionService;

    @Transactional
    public void saveOrUpdate(Long memberId, Long discussionId) {
        var discussion = characterDiscussionService.findById(discussionId);
        var like = characterDiscussionLikeService.saveOrUpdate(memberId, discussionId);
        discussion.updateLikeCount(like.getStatus());
    }
}
