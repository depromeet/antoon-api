package kr.co.antoon.character.infrastructure;

import kr.co.antoon.character.domain.CharacterImage;
import kr.co.antoon.character.domain.vo.CharacterImageType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CharacterImageRepository extends JpaRepository<CharacterImage, Long> {
    Optional<CharacterImage> findByCharacterIdAndType(Long characterId, CharacterImageType type);
}
