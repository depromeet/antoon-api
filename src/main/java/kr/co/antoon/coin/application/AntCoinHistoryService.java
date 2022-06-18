package kr.co.antoon.coin.application;

import kr.co.antoon.coin.domain.AntCoinHistory;
import kr.co.antoon.coin.domain.vo.RemittanceStatus;
import kr.co.antoon.coin.domain.vo.RemittanceType;
import kr.co.antoon.coin.dto.CoinHistory;
import kr.co.antoon.coin.infrastructure.AntCoinHistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class AntCoinHistoryService {
    private final AntCoinHistoryRepository antCoinHistoryRepository;

    @Transactional
    public void record(Long userId, Long walletId, Long amount, RemittanceStatus status, RemittanceType type, String reason) {
        var history = AntCoinHistory.builder()
                .userId(userId)
                .walletId(walletId)
                .amount(amount)
                .status(status)
                .type(type)
                .reason(reason)
                .build();

        antCoinHistoryRepository.save(history);
    }

    @Transactional
    public boolean checkTodayJoinWebtoon(Long userId, Long webtoonId) {
        String reason = "WEBTOONID_" + webtoonId;
        LocalDateTime today = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        today = Year.of(today.getYear())
                .atMonth(today.getMonthValue())
                .atDay(today.getDayOfMonth())
                .atTime(0,0,0,0);

        log.info("checktoday : {}", today);

        return antCoinHistoryRepository.existsByRemittanceTypeAndUserIdAndReasonAndCreatedAtAfter(
                RemittanceType.JOINED_WEBTOON,
                userId,
                reason,
                today // '2022-06-19T12:31.00.9999'
        );
    }

    @Transactional
    public CoinHistory getCoinHistory(Long userId) {
        return antCoinHistoryRepository.getAntCoinHistoryByUserId(userId);
    }
}