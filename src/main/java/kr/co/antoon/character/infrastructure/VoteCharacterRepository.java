package kr.co.antoon.character.infrastructure;

import kr.co.antoon.character.domain.CharacterVote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoteCharacterRepository extends JpaRepository<CharacterVote, Long> {
    Optional<CharacterVote> findByCharacterIdAndUserId(Long characterId, Long userId);
}
