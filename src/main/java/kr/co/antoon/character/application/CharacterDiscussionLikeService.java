package kr.co.antoon.character.application;

import kr.co.antoon.character.infrastructure.CharacterDiscussionLikeRepository;
import kr.co.antoon.character.domain.CharacterDiscussionLike;
import kr.co.antoon.oauth.dto.AuthInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CharacterDiscussionLikeService {
    private final CharacterDiscussionLikeRepository characterDiscussionLikeRepository;

    @Transactional
    public CharacterDiscussionLike saveOrUpdate(Long memberId, Long discussionId) {
        return characterDiscussionLikeRepository.save(
                characterDiscussionLikeRepository.findByUserIdAndDiscussionId(memberId, discussionId)
                        .map(CharacterDiscussionLike::update)
                        .orElse(CharacterDiscussionLike.builder()
                                .userId(memberId)
                                .discussionId(discussionId)
                                .build()
                        )
        );
    }

    @Transactional(readOnly = true)
    public Boolean isUserLike(AuthInfo info, Long discussionId) {
        if (info == null) {
            return false;
        }
        var like = characterDiscussionLikeRepository.findByUserIdAndDiscussionId(info.userId(), discussionId);
        if (like.isPresent()) {
            return like.get().getStatus();
        }
        return false;
    }
}
