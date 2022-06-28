package kr.co.antoon.coin.application;

import com.nimbusds.jose.shaded.json.JSONObject;
import com.nimbusds.jose.shaded.json.parser.JSONParser;
import kr.co.antoon.coin.AntCoinClient;
import kr.co.antoon.coin.domain.AntCoinHistory;
import kr.co.antoon.coin.domain.AntCoinWallet;
import kr.co.antoon.coin.domain.vo.CoinRewardType;
import kr.co.antoon.coin.domain.vo.RemittanceStatus;
import kr.co.antoon.coin.domain.vo.RemittanceType;
import kr.co.antoon.coin.dto.CoinHistory;
import kr.co.antoon.recommendation.domain.vo.RecommendationStatus;
import kr.co.antoon.recommendation.dto.response.RecommendationResponse;
import kr.co.antoon.vote.application.CandidateService;
import kr.co.antoon.webtoon.application.WebtoonService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AntCoinService implements AntCoinClient {
    private final AntCoinHistoryService antCoinHistoryService;
    private final AntCoinWalletService antCoinWalletService;
    private final WebtoonService webtoonService;
    private final CandidateService candidateService;

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
                reason,
                wallet.getWallet()
        );
    }

    @Override
    @Transactional
    public void minusCoin(Long userId, Long coin, String reason, RemittanceType type) {
        var wallet = getWallet(userId);
        wallet.minus(coin);

        reason = antCoinHistoryService.rewardReasonToJson(type, reason);

        antCoinHistoryService.record(
                userId,
                wallet.getId(),
                coin,
                RemittanceStatus.MINUS,
                type,
                reason,
                wallet.getWallet()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public AntCoinWallet getWallet(Long userId) {
        return antCoinWalletService.get(userId);
    }

    @SneakyThrows
    @Override
    @Transactional(readOnly = true)
    public List<CoinHistory> getCoinHistory(Long userId) {
        List<AntCoinHistory> antCoinHistories = antCoinHistoryService.getCoinHistory(userId);
        List<CoinHistory> coinHistories = new ArrayList<>();
        log.info("history of reason : {}", antCoinHistories.get(1).getReason());

        for(AntCoinHistory history : antCoinHistories) {
            String reason = history.getReason();
            if(reason.startsWith("{")) {
                JSONParser jsonParser = new JSONParser();
                Object obj = jsonParser.parse(reason);
                JSONObject jsonObj = (JSONObject) obj;
                log.info("json : {}", jsonObj);

                String key = jsonObj.keySet().iterator().next();
                log.info("json key : {}",key);

                if(key.contains("WEBTOON")) {
                    Long webtoonId = Long.parseLong(String.valueOf(jsonObj.get(key)));
                    reason = webtoonService.findById(webtoonId).getTitle();


                } else if(key.contains("VOTE")) {
                    Long candidateId = Long.parseLong(String.valueOf(jsonObj.get(key)));
                    reason = candidateService.findById(candidateId).getContent();
                }
            }


            coinHistories.add(
                    new CoinHistory(
                    history.getCreatedAt(),
                    history.getRemittanceStatus(),
                    history.getAmount(),
                    history.getWallet(),
                    history.getRemittanceType(),
                    reason
                    )
            );
        }
        return coinHistories;
    }

    @Transactional
    public void sign(Long userId) {
        if (!antCoinWalletService.existsByUserId(userId)) {
            var wallet = antCoinWalletService.save(userId);

            plusCoin(
                    userId,
                    CoinRewardType.DEFAULT_SIGN_COIN_BONUS.getAmount(),
                    "SIGNUP",
                    RemittanceType.SIGNED_SERVICE
            );
        }
    }

    @Transactional
    public RecommendationResponse joinWebtoon(Long userId, Long webtoonId, RecommendationResponse response, RecommendationStatus status) {
        if (antCoinHistoryService.checkTodayJoinWebtoon(userId, webtoonId)) {
            log.info("ALREADY_GET_COIN: 이미 탑승/하차를 통한 코인 지급이 완료되었습니다.");
            return response;
        }

        log.info("count : {}", antCoinHistoryService.countJoinWebtoon(userId));
        if(antCoinHistoryService.countJoinWebtoon(userId) >= rewardLimit) {
            log.info("ALREADY_OVER_COUNT_COIN: 금일 탑승/하차를 통한 코인 지급 횟수를 초과하였습니다.");
            return response;
        }

        RemittanceType type = RemittanceType.joinOrLeave(status);
//        var coinReason = new CoinReason(webtoonId);
//        var reason = MapperUtil.write(coinReason);

        String reason = antCoinHistoryService.rewardReasonToJson(type, webtoonId.toString());

        plusCoin(
                userId,
                CoinRewardType.JOINED_WETBOON_COIN_BONUS.getAmount(),
                reason,
                type
        );

        return response.update(true);
    }
}
