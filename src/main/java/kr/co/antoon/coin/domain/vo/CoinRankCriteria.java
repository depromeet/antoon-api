package kr.co.antoon.coin.domain.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CoinRankCriteria {
    LEVEL_ONE(0L),
    LEVEL_TWO(50L),
    LEVEL_THREE(300L),
    ;
    private final Long coinCriteria;
}
