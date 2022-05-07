package kr.co.antoon.graph.domain.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RankReason {
    VERSION_1("자체 알고리즘을 통한 랭킹 시스템 Version 1"),
    ;

    private final String description;
}
