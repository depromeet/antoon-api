package kr.co.antoon.discussion.infrastructure;

import kr.co.antoon.discussion.domain.DiscussionLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DiscussionLikeRepository extends JpaRepository<DiscussionLike, Long> {
    Optional<DiscussionLike> findByUserIdAndDiscussionId(Long userId, Long discussionId);
}
