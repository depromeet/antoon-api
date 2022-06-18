package kr.co.antoon.character.domain.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CharacterType {
    PERSONA("인물"),
    COUPLE("커플"),
    ;

    private final String description;
}
