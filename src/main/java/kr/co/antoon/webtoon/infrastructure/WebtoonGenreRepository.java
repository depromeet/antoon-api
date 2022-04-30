package kr.co.antoon.webtoon.infrastructure;

import kr.co.antoon.webtoon.domain.WebtoonGenre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WebtoonGenreRepository extends JpaRepository<WebtoonGenre, Long> {
    List<WebtoonGenre> findByWebtoonId(Long webtoonId);
}