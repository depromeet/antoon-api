package kr.co.antoon.coin.facade;

import kr.co.antoon.coin.application.AntCoinHistoryService;
import kr.co.antoon.coin.application.AntCoinWalletService;
import kr.co.antoon.coin.domain.vo.RemittanceStatus;
import kr.co.antoon.coin.domain.vo.RemittanceType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class AntCoinFacade {
    private final AntCoinWalletService antCoinWalletService;
    private final AntCoinHistoryService antCoinHistoryService;

    private final static Long DEFAULT_SIGN_COIN_BONUS = 100L;

    @Transactional
    public void sign(Long userId) {
        if (!antCoinWalletService.existsByUserId(userId)) {
            var wallet = antCoinWalletService.save(userId);

            antCoinHistoryService.record(
                    userId,
                    wallet.getId(),
                    DEFAULT_SIGN_COIN_BONUS,
                    RemittanceStatus.PLUS,
                    RemittanceType.SIGNED_SERVICE
            );
        }
    }
}