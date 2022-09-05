package kr.co.antoon.character.facade;

import kr.co.antoon.character.application.CharacterDiscussionLikeService;
import kr.co.antoon.character.application.CharacterDiscussionService;
import kr.co.antoon.character.application.CharacterService;
import kr.co.antoon.character.dto.reqeust.CharacterDiscussionRequest;
import kr.co.antoon.character.dto.response.CharacterDiscussionResponse;
import kr.co.antoon.oauth.dto.AuthInfo;
import kr.co.antoon.user.application.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class CharacterDiscussionFacade {
    private final CharacterService characterService;
    private final CharacterDiscussionLikeService characterDiscussionLikeService;
    private final CharacterDiscussionService characterDiscussionService;
    private final UserService userService;

    @Transactional
    public CharacterDiscussionResponse register(Long userId, Long characterId, CharacterDiscussionRequest request) {
        characterService.existsById(characterId);

        var discussion = characterDiscussionService.save(
                userId,
                characterId,
                request.content()
        );

        var user = userService.findById(discussion.getUserId());

        return new CharacterDiscussionResponse(
                characterId,
                discussion,
                user,
                false,
                characterDiscussionService.getTime(discussion.getCreatedAt())
        );
    }

    @Transactional
    public CharacterDiscussionResponse findById(AuthInfo info, Long discussionId) {
        var discussion = characterDiscussionService.findById(discussionId);
        var user = userService.findById(discussion.getUserId());
        var isUserLike = characterDiscussionLikeService.isUserLike(info, discussionId);

        return new CharacterDiscussionResponse(
                discussion.getCharacterId(),
                discussion,
                user,
                isUserLike,
                characterDiscussionService.getTime(discussion.getCreatedAt())
        );
    }

    @Transactional(readOnly = true)
    public Page<CharacterDiscussionResponse> findAll(AuthInfo info, Pageable pageable, Long characterId) {
        return characterDiscussionService.findByCharacterId(characterId, pageable)
                .map(discussion -> {
                    var user = userService.findById(discussion.getUserId());
                    var userLike = characterDiscussionLikeService.isUserLike(info, discussion.getId());

                    return new CharacterDiscussionResponse(
                            characterId,
                            discussion,
                            user,
                            userLike,
                            characterDiscussionService.getTime(discussion.getCreatedAt())
                    );
                });
    }

    @Transactional
    public CharacterDiscussionResponse update(AuthInfo info, Long discussionId, CharacterDiscussionRequest request) {
        var discussion = characterDiscussionService.update(discussionId, info.userId(), request);
        var user = userService.findById(discussion.getUserId());
        var isUserLike = characterDiscussionLikeService.isUserLike(info, discussionId);

        return new CharacterDiscussionResponse(
                discussion.getCharacterId(),
                discussion,
                user,
                isUserLike,
                characterDiscussionService.getTime(discussion.getCreatedAt())
        );
    }
}
