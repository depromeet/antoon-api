package kr.co.antoon.coin.domain.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RemittanceType {
    SIGNED_SERVICE("회원가입"),
    JOINED_WEBTOON("웹툰 탑승/하차"),
    VOTE("투표"),
    ;

    private final String description;
}
