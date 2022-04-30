package kr.co.antoon.webtoon.infrastructure;

import kr.co.antoon.webtoon.domain.WebtoonWriter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WebtoonWriterRepository extends JpaRepository<WebtoonWriter, Long> {
    List<WebtoonWriter> findByWebtoonId(Long webtoonId);
}