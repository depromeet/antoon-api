package kr.co.antoon.vote.infrastructure;

import kr.co.antoon.vote.domain.TopicDiscussion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicDiscussionRepository extends JpaRepository<TopicDiscussion, Long> {
    Page<TopicDiscussion> findByTopicId(Long topicId, Pageable pageable);
}
