package kr.co.antoon.webtoon.infrastructure;

import kr.co.antoon.webtoon.domain.WebtoonSnapshot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WebtoonSnapshotRepository extends JpaRepository<WebtoonSnapshot, Long> {
    Optional<WebtoonSnapshot> findTop1ByWebtoonIdOrderBySnapshotTimeDesc(Long webtoonId);
}
