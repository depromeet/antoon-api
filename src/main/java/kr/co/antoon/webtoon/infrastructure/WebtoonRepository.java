package kr.co.antoon.webtoon.infrastructure;

import kr.co.antoon.webtoon.domain.Webtoon;
import kr.co.antoon.webtoon.domain.vo.ActiveStatus;
import kr.co.antoon.webtoon.dto.WebtoonNativeDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WebtoonRepository extends JpaRepository<Webtoon, Long> {
    List<Webtoon> findAllByStatus(ActiveStatus status);

    @Query(value = """
            select w.* 
            from webtoon w 
            join webtoon_genre g 
            on w.id = g.id
            and w.status like %:status% 
            and g.genre_category like %:genre%
            """, nativeQuery = true)
    List<Webtoon> findByGenreAndStatus(@Param("genre") String genre, @Param("status") ActiveStatus status);

    long countByStatus(ActiveStatus status);

    @Query(value = """
            select w.id as webtoonId, w.title, w.content, w.webtoon_url as webtoonUrl, w.thumbnail, w.platform, w.status,
            wg.id as webtoonGenreId, wg.genre_category as genreCategory,
            wpd.id as webtoonPublishDayId, wpd.day,
            ww.id as webtoonWriterId, ww.name,
            rc.id as recommendationCountId, rc.join_count as joinCount, rc.leave_count as leaveCount
            from webtoon w
            join webtoon_genre wg on w.id = wg.webtoon_id
            join webtoon_publish_day wpd on w.id = wpd.webtoon_id
            join webtoon_writer ww on w.id = ww.webtoon_id
            left join recommendation_count rc on w.id = rc.webtoon_id
            where w.id = :webtoon_id
            """, nativeQuery = true)
    List<WebtoonNativeDto> findOneByWebtoonId(@Param(value = "webtoon_id") Long webtoonId);
}