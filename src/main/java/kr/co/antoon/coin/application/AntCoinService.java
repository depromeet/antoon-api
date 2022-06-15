package kr.co.antoon.coin.application;

import kr.co.antoon.coin.domain.AntCoinWallet;
import kr.co.antoon.coin.domain.vo.RemittanceStatus;
import kr.co.antoon.coin.domain.vo.RemittanceType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AntCoinService {
    private final AntCoinHistoryService antCoinHistoryService;
    private final AntCoinWalletService antCoinWalletService;

    @Transactional
    public void plusCoin(Long userId, Long coin, String reason, RemittanceType type) {
        var wallet = getWallet(userId);
        wallet.plus(coin);

        antCoinHistoryService.record(
                userId,
                wallet.getId(),
                coin,
                RemittanceStatus.PLUS,
                type,
                reason
        );
    }

    @Transactional
    public void minusCoin(Long userId, Long coin, String reason, RemittanceType type) {
        var wallet = getWallet(userId);
        wallet.minus(coin);

        antCoinHistoryService.record(
                userId,
                wallet.getId(),
                coin,
                RemittanceStatus.MINUS,
                type,
                reason
        );
    }

    @Transactional(readOnly = true)
    public AntCoinWallet getWallet(Long userId) {
        return antCoinWalletService.get(userId);
    }
}
