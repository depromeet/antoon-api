package kr.co.antoon.character.application;

import kr.co.antoon.character.domain.CharacterDiscussion;
import kr.co.antoon.character.dto.reqeust.CharacterDiscussionRequest;
import kr.co.antoon.character.infrastructure.CharacterDiscussionRepository;
import kr.co.antoon.discussion.domain.Discussion;
import kr.co.antoon.discussion.dto.query.DiscussionCountDto;
import kr.co.antoon.discussion.dto.request.DiscussionUpdateRequest;
import kr.co.antoon.error.dto.ErrorMessage;
import kr.co.antoon.error.exception.common.NotExistsException;
import kr.co.antoon.error.exception.oauth.NotValidRoleException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CharacterDiscussionService {
    private final CharacterDiscussionRepository characterDiscussionRepository;
    private final int SEC = 60, MIN = 60, HOUR = 24, DAY = 30, MONTH = 12;

    @Transactional
    public CharacterDiscussion save(Long userId, Long characterId, String content) {
        return characterDiscussionRepository.save(
                CharacterDiscussion.builder()
                        .userId(userId)
                        .characterId(characterId)
                        .content(content)
                        .build()
        );
    }

    @Transactional(readOnly = true)
    public CharacterDiscussion findById(Long id) {
        return characterDiscussionRepository.findById(id)
                .orElseThrow(() -> new NotExistsException(ErrorMessage.NOT_EXISTS_DISCUSSION_ERROR));
    }

    @Transactional(readOnly = true)
    public Page<CharacterDiscussion> findAll(Pageable pageable) {
        return characterDiscussionRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Page<CharacterDiscussion> findByCharacterId(Long characterId, Pageable pageable) {
        return characterDiscussionRepository.findByCharacterId(characterId, pageable);
    }

    @Transactional
    public CharacterDiscussion update(Long discussionId, Long userId, CharacterDiscussionRequest request) {
        var discussion = characterDiscussionRepository.findById(discussionId)
                .orElseThrow(() -> new NotExistsException(ErrorMessage.NOT_EXISTS_DISCUSSION_ERROR));
        if(!discussion.getUserId().equals(userId)) {
            throw new NotValidRoleException(ErrorMessage.NOT_VALID_ROLE_ERROR);
        }
        discussion.update(request.content());
        return discussion;
    }

    @Transactional
    public void delete(Long discussionId, Long userId) {
        var discussion = characterDiscussionRepository.findById(discussionId)
                .orElseThrow(() -> new NotExistsException(ErrorMessage.NOT_EXISTS_DISCUSSION_ERROR));
        if(!discussion.getUserId().equals(userId)) {
            throw new NotValidRoleException(ErrorMessage.NOT_VALID_ROLE_ERROR);
        }
        characterDiscussionRepository.deleteById(discussionId);
    }

    @Transactional(readOnly = true)
    public String getTime(LocalDateTime time) {
        LocalDateTime now = LocalDateTime.now();
        long diffTime = time.until(now, ChronoUnit.SECONDS);

        if (diffTime < SEC) {
            return diffTime + "초전";
        }
        diffTime = diffTime / SEC;
        if (diffTime < MIN) {
            return diffTime + "분전";
        }
        diffTime = diffTime / MIN;
        if (diffTime < HOUR) {
            return diffTime + "시간전";
        }
        diffTime = diffTime / HOUR;
        if (diffTime < DAY) {
            return diffTime + "일전";
        }
        diffTime = diffTime / DAY;
        if (diffTime < MONTH) {
            return diffTime + "개월전";
        }
        return time.toLocalDate().toString();
    }
}
