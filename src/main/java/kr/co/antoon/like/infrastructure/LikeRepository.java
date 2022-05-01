package kr.co.antoon.like.infrastructure;

import kr.co.antoon.like.domain.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {

    Optional<Like> findByUserIdAndDiscussionId(Long userId, Long discussionId);
}
