package kr.co.antoon.coin.infrastructure;

import io.lettuce.core.dynamic.annotation.Param;
import kr.co.antoon.coin.domain.AntCoinHistory;
import kr.co.antoon.coin.domain.vo.RemittanceType;
import kr.co.antoon.coin.dto.CoinHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Repository
public interface AntCoinHistoryRepository extends JpaRepository<AntCoinHistory, Long> {
    boolean existsByRemittanceTypeAndUserIdAndReasonAndCreatedAtAfter(RemittanceType remittanceType, Long userId, String reason, LocalDateTime now);
    CoinHistory getAntCoinHistoryByUserId(Long userId);
    int countByUserIdAndCreatedAtAfter(Long userId, LocalDateTime now);
}