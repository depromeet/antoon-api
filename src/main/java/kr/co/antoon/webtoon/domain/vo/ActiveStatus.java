package kr.co.antoon.webtoon.domain.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ActiveStatus {
    PUBLISH("연재"),
    PAUSE("휴재"),
    ;

    private final String description;
}

