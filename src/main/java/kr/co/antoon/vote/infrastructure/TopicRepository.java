package kr.co.antoon.vote.infrastructure;

import kr.co.antoon.vote.domain.Topic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {
    Page<Topic> findAllByTopicVoteTimeLessThan(LocalDateTime now, Pageable pageable);

    Page<Topic> findAllByOrderByCreatedAtDesc(Pageable pageable);

    Page<Topic> findAllByOrderByJoinCountDesc(Pageable pageable);

    List<Topic> findTop8ByOrderByJoinCountDesc();
}
