package kr.co.antoon.webtoon.infrastructure;

import kr.co.antoon.webtoon.domain.Webtoon;
import kr.co.antoon.webtoon.domain.vo.ActiveStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WebtoonRepository extends JpaRepository<Webtoon, Long> {
    List<Webtoon> findAllByStatus(ActiveStatus status);

    /**
     * 검색 조회를 위한 Query
     **/
    List<Webtoon> findAllByTitleContainingIgnoreCase(String title);
}