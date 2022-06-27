package kr.co.antoon.coin.domain.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CoinUsageType {
    VOTED_TOPIC("A/B 투표 시 사용 코인", 3L),
    ;

    private final String description;
    private final Long amount;
}
