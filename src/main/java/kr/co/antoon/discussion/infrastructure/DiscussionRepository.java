package kr.co.antoon.discussion.infrastructure;

import kr.co.antoon.discussion.domain.Discussion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscussionRepository extends JpaRepository<Discussion, Long> {
    long countByWebtoonId(Long webtoonId);
}