package kr.co.antoon.character.domain.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum VoteType {
    CHARACTER("인물"),
    COUPLE("커플"),
    ;

    private final String description;
}
