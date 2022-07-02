package kr.co.antoon.character.application;

import kr.co.antoon.character.domain.CharacterDiscussion;
import kr.co.antoon.character.dto.reqeust.CharacterDiscussionRequest;
import kr.co.antoon.character.infrastructure.CharacterDiscussionRepository;
import kr.co.antoon.common.util.TimeUtil;
import kr.co.antoon.error.dto.ErrorMessage;
import kr.co.antoon.error.exception.common.NotExistsException;
import kr.co.antoon.error.exception.oauth.NotValidRoleException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static kr.co.antoon.character.application.CharacterDiscussionService.CharacterDiscussionTime.DAY;
import static kr.co.antoon.character.application.CharacterDiscussionService.CharacterDiscussionTime.HOUR;
import static kr.co.antoon.character.application.CharacterDiscussionService.CharacterDiscussionTime.MIN;
import static kr.co.antoon.character.application.CharacterDiscussionService.CharacterDiscussionTime.MONTH;
import static kr.co.antoon.character.application.CharacterDiscussionService.CharacterDiscussionTime.SEC;

@Service
@RequiredArgsConstructor
public class CharacterDiscussionService {
    private final CharacterDiscussionRepository characterDiscussionRepository;

    @Getter
    @RequiredArgsConstructor
    enum CharacterDiscussionTime {
        SEC(60),
        MIN(60),
        HOUR(24),
        DAY(30),
        MONTH(12),
        ;

        private final int value;
    }

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
        if (!discussion.getUserId().equals(userId)) {
            throw new NotValidRoleException(ErrorMessage.NOT_VALID_ROLE_ERROR);
        }
        discussion.update(request.content());
        return discussion;
    }

    @Transactional
    public void delete(Long discussionId, Long userId) {
        var discussion = characterDiscussionRepository.findById(discussionId)
                .orElseThrow(() -> new NotExistsException(ErrorMessage.NOT_EXISTS_DISCUSSION_ERROR));
        if (!discussion.getUserId().equals(userId)) {
            throw new NotValidRoleException(ErrorMessage.NOT_VALID_ROLE_ERROR);
        }
        characterDiscussionRepository.deleteById(discussionId);
    }

    // 수정 필요
    @Transactional(readOnly = true)
    public String getTime(LocalDateTime time) {
        LocalDateTime now = TimeUtil.now();
        long diffTime = time.until(now, ChronoUnit.SECONDS);

        if (diffTime < SEC.value) {
            return diffTime + "초전";
        }
        diffTime = diffTime / SEC.value;
        if (diffTime < MIN.value) {
            return diffTime + "분전";
        }
        diffTime = diffTime / MIN.value;
        if (diffTime < HOUR.value) {
            return diffTime + "시간전";
        }
        diffTime = diffTime / HOUR.value;
        if (diffTime < DAY.value) {
            return diffTime + "일전";
        }
        diffTime = diffTime / DAY.value;
        if (diffTime < MONTH.value) {
            return diffTime + "개월전";
        }
        return time.toLocalDate().toString();
    }
}
