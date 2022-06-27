package kr.co.antoon.coin.domain.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CoinRewardType {
    DEFAULT_SIGN_COIN_BONUS("회원가입 시 지급 코인", 30L),
    JOINED_WETBOON_COIN_BONUS("웹툰 탑승/하차 시 지급 코인", 3L),
    ;

    private final String description;
    private final Long amount;
}
