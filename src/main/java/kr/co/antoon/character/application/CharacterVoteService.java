package kr.co.antoon.character.application;

import kr.co.antoon.character.domain.CharacterVote;
import kr.co.antoon.character.infrastructure.VoteCharacterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CharacterVoteService {
    private final VoteCharacterRepository voteCharacterRepository;

    @Transactional
    public CharacterVote save(Long characterId, Long userId) {
        CharacterVote voteCharacter = new CharacterVote(characterId, userId);
        return voteCharacterRepository.save(voteCharacter);
    }

    @Transactional(readOnly = true)
    public Boolean isUserJoin(Long characterId, Long userId) {
        var characterVote = voteCharacterRepository.findByCharacterIdAndUserId(characterId, userId);
        if(characterVote.isPresent()) {
            return characterVote.get().getStatus();
        }
        return false;
    }
}
