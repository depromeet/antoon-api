package kr.co.antoon.discussion.infrastructure;

import kr.co.antoon.discussion.domain.Discussion;
import kr.co.antoon.discussion.dto.query.DiscussionCountDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DiscussionRepository extends JpaRepository<Discussion, Long> {
    @Query(value = """
                SELECT d.webtoon_id AS webtoonId, count(*) AS discussionCount
                FROM discussion d
                WHERE d.created_at BETWEEN :before_one_hour AND :now
            """, nativeQuery = true)
    List<DiscussionCountDto> countAllDiscussion(
            @Param(value = "before_one_hour") LocalDateTime beforeOneHour,
            @Param(value = "now") LocalDateTime now
    );

    Page<Discussion> findByWebtoonId(Long webtoonId, Pageable pageable);
}
