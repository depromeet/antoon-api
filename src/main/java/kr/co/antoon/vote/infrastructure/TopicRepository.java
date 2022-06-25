package kr.co.antoon.vote.infrastructure;

import kr.co.antoon.vote.domain.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {
    List<Topic> findAllByTopicVoteTimeLessThan(LocalDateTime now);

    List<Topic> findAllByOrderByCreatedAtDesc();

    List<Topic> findAllByOrderByJoinCountDesc();

    List<Topic> findTop8ByOrderByJoinCountDesc();
}
