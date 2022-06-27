package kr.co.antoon.character.infrastructure;

import kr.co.antoon.character.domain.CharacterDiscussion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CharacterDiscussionRepository extends JpaRepository<CharacterDiscussion, Long> {
    Page<CharacterDiscussion> findByCharacterId(Long characterId, Pageable pageable);
}
