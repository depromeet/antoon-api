package kr.co.antoon.webtoon.infrastructure;

import kr.co.antoon.webtoon.domain.Webtoon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WebtoonRepository extends JpaRepository<Webtoon, Long> {
}