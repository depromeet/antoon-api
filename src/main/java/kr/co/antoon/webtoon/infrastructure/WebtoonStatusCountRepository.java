package kr.co.antoon.webtoon.infrastructure;

import kr.co.antoon.webtoon.domain.WebtoonStatusCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WebtoonStatusCountRepository extends JpaRepository<WebtoonStatusCount, Long> {
    Optional<WebtoonStatusCount> findByWebtoonId(Long webtoonId);
}
