package kr.co.antoon.webtoon.infrastructure;

import kr.co.antoon.webtoon.domain.WebtoonPublishDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WebtoonPublishDayRepository extends JpaRepository<WebtoonPublishDay, Long> {
    boolean existsByWebtoonIdAndDay(Long wetoonId, String day);

    List<WebtoonPublishDay> findAllByWebtoonId(Long webtoonId);
}