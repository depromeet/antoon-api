package kr.co.antoon.coin.facade;

import kr.co.antoon.coin.application.AntCoinHistoryService;
import kr.co.antoon.coin.application.AntCoinService;
import kr.co.antoon.coin.application.AntCoinWalletService;
import kr.co.antoon.coin.domain.vo.RemittanceStatus;
import kr.co.antoon.coin.domain.vo.RemittanceType;
import kr.co.antoon.error.dto.ErrorMessage;
import kr.co.antoon.error.exception.common.AlreadyExistsException;
import kr.co.antoon.recommendation.dto.response.RecommendationResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
@Slf4j
@Component
@RequiredArgsConstructor
public class AntCoinFacade {
    private final AntCoinWalletService antCoinWalletService;
    private final AntCoinHistoryService antCoinHistoryService;
    private final AntCoinService antCoinService;

    private final static Long DEFAULT_SIGN_COIN_BONUS = 100L;
    private final static Long JOINED_WETBOON_COIN_BONUS = 3L;

    @Transactional
    public void sign(Long userId) {
        if (!antCoinWalletService.existsByUserId(userId)) {
            var wallet = antCoinWalletService.save(userId);

            antCoinHistoryService.record(
                    userId,
                    wallet.getId(),
                    DEFAULT_SIGN_COIN_BONUS,
                    RemittanceStatus.PLUS,
                    RemittanceType.SIGNED_SERVICE,
                    "SIGNUP"
            );
        }
    }

    @Transactional
    public RecommendationResponse joinWebtoon(Long userId, Long webtoonId, RecommendationResponse response) {
        if(antCoinHistoryService.checkTodayJoinWebtoon(userId, webtoonId)) {
            log.info("ALREADY_GET_COIN: 이미 탑승/하차를 통한 코인 지급이 완료되었습니다.");
            return response;
        }

        String reason = "WEBTOONID_"+webtoonId;
        antCoinService.plusCoin(userId, JOINED_WETBOON_COIN_BONUS, reason, RemittanceType.JOINED_WEBTOON);
        return response.update(true);
    }
}