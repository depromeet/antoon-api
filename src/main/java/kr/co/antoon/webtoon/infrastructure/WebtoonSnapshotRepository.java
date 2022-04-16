package kr.co.antoon.webtoon.infrastructure;

import kr.co.antoon.webtoon.domain.WebtoonSnapshot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface WebtoonSnapshotRepository extends JpaRepository<WebtoonSnapshot, Long> {
    List<WebtoonSnapshot> findAllBySnapshotTime(LocalDate time);
}