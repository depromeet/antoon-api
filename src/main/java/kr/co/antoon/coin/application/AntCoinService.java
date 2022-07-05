package kr.co.antoon.coin.application;

import com.nimbusds.jose.shaded.json.JSONObject;
import com.nimbusds.jose.shaded.json.parser.JSONParser;
import kr.co.antoon.character.application.CharacterService;
import kr.co.antoon.coin.AntCoinClient;
import kr.co.antoon.coin.domain.AntCoinHistory;
import kr.co.antoon.coin.domain.AntCoinWallet;
import kr.co.antoon.coin.domain.vo.CoinRewardType;
import kr.co.antoon.coin.domain.vo.RemittanceStatus;
import kr.co.antoon.coin.domain.vo.RemittanceType;
import kr.co.antoon.coin.dto.CoinHistoryResponse;
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
    private final CharacterService characterService;

    @Override
    @Transactional
    public void plusCoin(Long userId, Long coin, String reason, RemittanceType type) {
        var wallet = antCoinWalletService.findByUserId(userId);
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
        var wallet = antCoinWalletService.findByUserId(userId);
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
    public AntCoinWallet getWallet(Long userId) {
        return antCoinWalletService.findByUserId(userId);
    }

    @SneakyThrows
    @Override
    @Transactional(readOnly = true)
    public CoinHistoryResponse getCoinHistory(Long userId) {
        var antCoinHistories = antCoinHistoryService.getCoinHistory(userId);
        List<CoinHistoryResponse.CoinHistory> coinHistories = new ArrayList<>();

        for (AntCoinHistory history : antCoinHistories) {
            String reason = history.getReason();
            if (reason.startsWith("{")) {
                var jsonParser = new JSONParser();
                var obj = jsonParser.parse(reason);
                var jsonObj = (JSONObject) obj;
                var key = jsonObj.keySet().iterator().next();

                var id = Long.parseLong(String.valueOf(jsonObj.get(key)));

                if (key.contains("WEBTOON")) {
                    reason = webtoonService.findById(id).getTitle();
                } else if (key.contains("VOTE")) {
                    reason = candidateService.findById(id).getContent();
                } else if (key.contains("CHARACTER")) {
                    reason = characterService.findById(id).getName();
                }
            }

            coinHistories.add(
                    new CoinHistoryResponse.CoinHistory(
                            history.getCreatedAt(),
                            history.getRemittanceStatus(),
                            history.getAmount(),
                            history.getWallet(),
                            history.getRemittanceType(),
                            reason
                    )
            );
        }
        return new CoinHistoryResponse(coinHistories);
    }

    @Transactional
    public void create(Long userId) {
        if (!antCoinWalletService.existsByUserId(userId)) {
            antCoinWalletService.save(userId);
        }
    }

    // TODO : plus, minus, get, exists 등 아주 기본적인 것들만 antclient에 있어야함 -> 해당 로직과 같은 것들은 여기 있으면 않됨
    @Override
    @Transactional
    public RecommendationResponse joinWebtoon(Long userId, Long webtoonId, RecommendationResponse response, RecommendationStatus status) {
        if (antCoinHistoryService.checkTodayJoinWebtoon(userId, webtoonId)) {
            log.info("ALREADY_GET_COIN: 이미 탑승/하차를 통한 코인 지급이 완료되었습니다.");
            return response;
        }

        log.info("count : {}", antCoinHistoryService.countJoinWebtoon(userId));
        if (antCoinHistoryService.countJoinWebtoon(userId) >= rewardLimit) {
            log.info("ALREADY_OVER_COUNT_COIN: 금일 탑승/하차를 통한 코인 지급 횟수를 초과하였습니다.");
            return response;
        }

        var type = RemittanceType.joinOrLeave(status);

        var reason = antCoinHistoryService.rewardReasonToJson(type, webtoonId.toString());

        plusCoin(
                userId,
                CoinRewardType.JOINED_WETBOON_COIN_BONUS.getAmount(),
                reason,
                type
        );

        return response.update(true);
    }
}
