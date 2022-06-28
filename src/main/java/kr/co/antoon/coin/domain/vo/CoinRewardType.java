package kr.co.antoon.coin.domain.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CoinRewardType {
    DEFAULT_SIGN_COIN_BONUS("회원가입 시 지급 코인", 30L),
    VOTE_COIN_BONUS("투표 리워드 시 지급 코인", 10L),
    JOINED_WETBOON_COIN_BONUS("웹툰 탑승/하차 시 지급 코인", 1L),
    JOINED_CHARACTER_COIN_BONUS("인물/커플 탑승 시 차감 코인", 5L),
    VOTE_USAGE_COIN("투표 참여 시 차감 코인", 3L)
    ;

    private final String description;
    private final Long amount;
}
