package kr.co.antoon.coin.application;

import kr.co.antoon.coin.AntCoinClient;
import kr.co.antoon.coin.domain.AntCoinWallet;
import kr.co.antoon.coin.domain.vo.CoinRewardType;
import kr.co.antoon.coin.domain.vo.RemittanceStatus;
import kr.co.antoon.coin.domain.vo.RemittanceType;
import kr.co.antoon.coin.dto.CoinHistory;
import kr.co.antoon.coin.dto.CoinReason;
import kr.co.antoon.common.util.MapperUtil;
import kr.co.antoon.recommendation.dto.response.RecommendationResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AntCoinService implements AntCoinClient {
    private final AntCoinHistoryService antCoinHistoryService;
    private final AntCoinWalletService antCoinWalletService;

    @Override
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

    @Override
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

    @Override
    @Transactional(readOnly = true)
    public AntCoinWallet getWallet(Long userId) {
        return antCoinWalletService.get(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public CoinHistory getCoinHistory(Long userId) {
        return antCoinHistoryService.getCoinHistory(userId);
    }

    @Transactional
    public void sign(Long userId) {
        if (!antCoinWalletService.existsByUserId(userId)) {
            var wallet = antCoinWalletService.save(userId);

            antCoinHistoryService.record(
                    userId,
                    wallet.getId(),
                    CoinRewardType.DEFAULT_SIGN_COIN_BONUS.getAmount(),
                    RemittanceStatus.PLUS,
                    RemittanceType.SIGNED_SERVICE,
                    "SIGNUP"
            );
        }
    }

    @Transactional
    public RecommendationResponse joinWebtoon(Long userId, Long webtoonId, RecommendationResponse response) {
        if (antCoinHistoryService.checkTodayJoinWebtoon(userId, webtoonId)) {
            log.info("ALREADY_GET_COIN: 이미 탑승/하차를 통한 코인 지급이 완료되었습니다.");
            return response;
        }

        var coinReason = new CoinReason(webtoonId);

        var reason = MapperUtil.write(coinReason);

        plusCoin(
                userId,
                CoinRewardType.JOINED_WETBOON_COIN_BONUS.getAmount(),
                reason,
                RemittanceType.JOINED_WEBTOON
        );

        return response.update(true);
    }
}
