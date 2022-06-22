package kr.co.antoon.vote.domain.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TopicCategory {
    AB("ab"),
    CHOICE("choice"),
    ;

    private final String description;
}
