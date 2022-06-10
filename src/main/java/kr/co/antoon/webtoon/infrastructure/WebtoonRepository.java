package kr.co.antoon.webtoon.infrastructure;

import kr.co.antoon.webtoon.domain.Webtoon;
import kr.co.antoon.webtoon.domain.vo.ActiveStatus;
import kr.co.antoon.webtoon.dto.query.WebtoonDayNativeDto;
import kr.co.antoon.webtoon.dto.query.WebtoonGenreBannerNativeDto;
import kr.co.antoon.webtoon.dto.query.WebtoonGenreNativeDto;
import kr.co.antoon.webtoon.dto.query.WebtoonNativeDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WebtoonRepository extends JpaRepository<Webtoon, Long>, JpaSpecificationExecutor<Webtoon> {
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
            rc.id as recommendationCountId, rc.join_count as joinCount, rc.leave_count as leaveCount,
            gss.graph_score as graphScore, gss.score_gap as scoreGap,
            r.status as recommendationStatus,
            tr.ranking as ranking
            from webtoon w
            left join webtoon_genre wg on w.id = wg.webtoon_id
            left join webtoon_publish_day wpd on w.id = wpd.webtoon_id
            left join webtoon_writer ww on w.id = ww.webtoon_id
            left join graph_score_snapshot gss on w.id = gss.webtoon_id
            left join top_rank tr on w.id = tr.webtoon_id
			left join recommendation_count rc on w.id = rc.webtoon_id
            left join recommendation r on w.id = r.webtoon_id
            where w.id = :webtoon_id and gss.snapshot_time between :start_time and :end_time
            """, nativeQuery = true)
    List<WebtoonNativeDto> findOneByWebtoonId(
            @Param(value = "webtoon_id") Long webtoonId,
            @Param(value = "start_time") String startTime,
            @Param(value = "end_time") String endTime
    );

    // TODO : 조회 정렬에 대해 수정 필요
    @Query(nativeQuery = true, value = """
                    select w.id as webtoonId, w.title, w.thumbnail, wpd.day
                    from webtoon w
                    join webtoon_publish_day wpd on w.id = wpd.webtoon_id
                    join graph_score_snapshot gss on w.id = gss.webtoon_id
                    where wpd.day = :day
            """,
            countQuery = """
                            select count(*)
                            from webtoon w
                            join webtoon_publish_day wpd on w.id = wpd.webtoon_id
                            where wpd.day = :day
                    """
    )
    Page<WebtoonDayNativeDto> findAllByDay(@Param(value = "day") String day, Pageable pageable);

    @Query(nativeQuery = true, value = """
                select wg.genre_category as genreCategory, w.thumbnail
                from webtoon w
                join webtoon_genre wg on w.id = wg.webtoon_id
                join graph_score_snapshot gss on w.id = gss.webtoon_id
                where gss.snapshot_time between :start_time and :end_time
                and w.status = 'PUBLISH' and wg.genre_category = :category and w.thumbnail is not null or ''
                order by gss.score_gap desc limit 3
            """)
    List<WebtoonGenreBannerNativeDto> findGenre(
            @Param(value = "start_time") String startTime,
            @Param(value = "end_time") String endTime,
            @Param(value = "category") String category
    );

    @Query(nativeQuery = true, value = """
                        select w.id as webtoonId, w.thumbnail, w.title, w.platform,
                        gss.graph_score as graphScore, gss.score_gap as scoreGap
                        from webtoon w
                        join webtoon_genre wg on w.id = wg.webtoon_id
                        join graph_score_snapshot gss on w.id = gss.webtoon_id
                        where gss.snapshot_time between :start_time and :end_time
                        and w.status = 'PUBLISH' and wg.genre_category = :category and w.thumbnail is not null or ''
                        order by gss.score_gap
            """,
            countQuery = """
                                            select count(*)
                                            from webtoon w
                                            join webtoon_genre wg on w.id = wg.webtoon_id
                                            join graph_score_snapshot gss on w.id = gss.webtoon_id
                                            where gss.snapshot_time between :start_time and :end_time
                                            and w.status = 'PUBLISH' and wg.genre_category = :category and w.thumbnail is not null or ''
                                            order by gss.score_gap
                    """)
    Page<WebtoonGenreNativeDto> findAllByGenre(
            @Param(value = "start_time") String startTime,
            @Param(value = "end_time") String endTime,
            @Param(value = "category") String category,
            Pageable pageable
    );
}