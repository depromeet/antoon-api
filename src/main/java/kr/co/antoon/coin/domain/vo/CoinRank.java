package kr.co.antoon.coin.domain.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CoinRank {
    LEVEL_ONE("거지개미"),
    LEVEL_TWO("일개미"),
    LEVEL_THREE("부자개미"),
    ;
    private final String description;
}
