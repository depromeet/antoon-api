package kr.co.toonzip.webtoon.infrastructure;

import kr.co.toonzip.webtoon.domain.WebtoonPublishDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WebtoonPublishDayRepository extends JpaRepository<WebtoonPublishDay, Long> {
}
