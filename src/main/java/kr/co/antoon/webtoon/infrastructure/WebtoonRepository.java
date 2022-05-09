package kr.co.antoon.webtoon.infrastructure;

import kr.co.antoon.webtoon.domain.Webtoon;
import kr.co.antoon.webtoon.domain.vo.ActiveStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WebtoonRepository extends JpaRepository<Webtoon, Long> {
    List<Webtoon> findAllByStatus(ActiveStatus status);

    /**
     * 검색 조회를 위한 Query
     **/
    List<Webtoon> findAllByTitleContainingIgnoreCase(String title);

    @Query(value = """
            select w.* 
            from webtoon w 
            join webtoon_genre g 
            on w.id = g.id
            join graph_score_snapshot s
            on w.id = s.webtoon_id
            and w.status like %:status% 
            and g.genre_category like %:genre%
            order by s.score_gap desc
            """,
            nativeQuery = true)
    List<Webtoon> findByGenreAndStatus(@Param("genre") String genre, @Param("status") ActiveStatus status, Pageable pageable);
}