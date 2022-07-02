package kr.co.antoon.character.infrastructure;

import kr.co.antoon.character.domain.CharacterHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CharacterHistoryRepository extends JpaRepository<CharacterHistory, Long> {
    Optional<CharacterHistory> findByCharacterIdAndUserId(Long characterId, Long userId);

    Long countByCharacterId(Long characterId);

    Boolean existsByUserIdAndCharacterId(Long userId, Long characterId);
}
