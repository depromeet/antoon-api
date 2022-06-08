package kr.co.antoon.coin.domain.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RemittanceType {
    JOINED_WEBTOON("탑승/하차 로직"),
    ;

    private final String description;
}