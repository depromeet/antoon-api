package kr.co.antoon.coin.infrastructure;

import io.lettuce.core.dynamic.annotation.Param;
import kr.co.antoon.coin.domain.AntCoinHistory;
import kr.co.antoon.coin.domain.vo.RemittanceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Repository
public interface AntCoinHistoryRepository extends JpaRepository<AntCoinHistory, Long> {

//    @Query(value = """
//            select EXISTS(
//                select *
//                from ant_coin_history
//                where remittance_type = 'JOINED_WEBTOON'
//                and user_id = :userId
//                and reason = :reason
//            /    and created_at like :now%)
//                as exist
//            """, nativeQuery = true)
//    int existsTodayByUserIdAndWebtoonId(@Param("userId") Long userId, @Param("reason") String reason, @Param("now") String now);

    boolean existsByRemittanceTypeAndUserIdAndReasonAndCreatedAtAfter(RemittanceType remittanceType, Long userId, String reason, LocalDateTime now);
}