package kr.co.antoon.webtoon.infrastructure;

import kr.co.antoon.webtoon.domain.WebtoonStatus;
import kr.co.antoon.webtoon.domain.vo.WebtoonStatusType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WebtoonStatusRepository extends JpaRepository<WebtoonStatus, Long> {
    Boolean existsByUserIdAndWebtoonIdAndStatus(long userId, long webtoonId, WebtoonStatusType status);
}
