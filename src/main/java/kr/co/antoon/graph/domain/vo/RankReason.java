package kr.co.antoon.graph.domain.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RankReason {
    TOTAL_SCORE("총 점수 비교", "자체 알고리즘을 통한 랭킹 시스템 Version 1"),
    ;

    private final String title;
    private final String description;
}
