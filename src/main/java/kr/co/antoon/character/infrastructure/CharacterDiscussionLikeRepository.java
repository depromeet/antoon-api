package kr.co.antoon.character.infrastructure;

import kr.co.antoon.character.domain.CharacterDiscussionLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CharacterDiscussionLikeRepository extends JpaRepository<CharacterDiscussionLike, Long> {
    Optional<CharacterDiscussionLike> findByUserIdAndDiscussionId(Long userId, Long discussionId);
}
