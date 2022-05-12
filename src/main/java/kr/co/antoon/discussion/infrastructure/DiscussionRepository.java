package kr.co.antoon.discussion.infrastructure;

import kr.co.antoon.discussion.domain.Discussion;
import kr.co.antoon.discussion.dto.DiscussionCountDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiscussionRepository extends JpaRepository<Discussion, Long> {
    long countByWebtoonId(Long webtoonId);

    @Query(value = """
                SELECT 
                    d.webtoon_id AS webtoonId, 
                    count(*) AS discussionCount
                FROM discussion d 
            """, nativeQuery = true)
    List<DiscussionCountDto> countAllDiscussion();
}