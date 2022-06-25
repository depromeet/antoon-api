package kr.co.antoon.vote.infrastructure;

import kr.co.antoon.vote.domain.TopicDiscussionLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TopicDiscussionLikeRepository extends JpaRepository<TopicDiscussionLike, Long> {
    Optional<TopicDiscussionLike> findByUserIdAndTopicCommentId(Long userId, Long topicCommentId);
}
