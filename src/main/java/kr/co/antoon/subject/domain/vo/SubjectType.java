package kr.co.antoon.subject.domain.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SubjectType {
    CHARACTER("인물"),
    COUPLE("커플"),
    ;

    private final String description;
}
