package kr.co.antoon.coin.infrastructure;

import kr.co.antoon.coin.domain.AntCoinHistory;
import kr.co.antoon.coin.domain.vo.RemittanceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AntCoinHistoryRepository extends JpaRepository<AntCoinHistory, Long> {
    boolean existsByRemittanceTypeAndUserIdAndReasonAndCreatedAtAfter(RemittanceType remittanceType, Long userId, String reason, LocalDateTime now);

    List<AntCoinHistory> getAntCoinHistoryByUserIdOrderByCreatedAtDesc(Long userId);

    @Query(value = """
                    select count(*) as count
            from ant_coin_history 
            where user_id = :userId and 
            created_at like :today% and 
            remittance_type like %:type
                """, nativeQuery = true)
    Long countTodayWebtoonReward(
            @Param(value = "userId") Long userId,
            @Param(value = "today") String today,
            @Param(value = "type") String type
    );

    boolean existsByUserIdAndRemittanceType(Long userId, RemittanceType remittanceType);
}
