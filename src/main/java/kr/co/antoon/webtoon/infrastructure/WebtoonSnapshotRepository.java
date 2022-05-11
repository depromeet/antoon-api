package kr.co.antoon.webtoon.infrastructure;

import kr.co.antoon.webtoon.domain.WebtoonSnapshot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface WebtoonSnapshotRepository extends JpaRepository<WebtoonSnapshot, Long> {
    List<WebtoonSnapshot> findAllBySnapshotTime(LocalDate time);

    Optional<WebtoonSnapshot> findTop1ByWebtoonIdOrderBySnapshotTimeDesc(Long webtoonId);
}