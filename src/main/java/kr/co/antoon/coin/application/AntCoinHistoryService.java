package kr.co.antoon.coin.application;

import kr.co.antoon.coin.domain.AntCoinHistory;
import kr.co.antoon.coin.domain.vo.RemittanceStatus;
import kr.co.antoon.coin.domain.vo.RemittanceType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AntCoinHistoryService {
    @Transactional
    public void record(Long userId, Long walletId, Long amount, RemittanceStatus status, RemittanceType type) {
        var history = AntCoinHistory.builder()
                .userId(userId)
                .walletId(walletId)
                .amount(amount)
                .status(status)
                .type(type)
                .build();
    }
}