package kr.co.antoon.vote.domain.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SortType {
    LATEST("최신순"),
    RANKS("인기순"),
    CLOSES("종료됨"),
    ;

    private final String description;
}
