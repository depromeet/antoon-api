package kr.co.toonzip.webtoon.infrastructure;

import kr.co.toonzip.webtoon.domain.WebtoonSnapshot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WebtoonSnapshotRepository extends JpaRepository<WebtoonSnapshot, Long> {
}