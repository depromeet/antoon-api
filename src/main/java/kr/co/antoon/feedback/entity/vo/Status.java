package kr.co.antoon.feedback.entity.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Status {
    REGISTERED("등록"),
    PROCEED("진행중"),
    COMPLETED("확인"),
    ;

    private final String description;
}