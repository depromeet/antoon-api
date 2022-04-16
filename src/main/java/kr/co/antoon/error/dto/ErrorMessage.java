package kr.co.antoon.error.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorMessage {
    CONFLICT_ERROR("예기치 못한 에러가 발생했습니다."),
    NOT_EXIST_USER("존재하지 않는 사용자입니다."),
    EXPIRED_TOKEN("해당 토큰은 만료된 토큰입니다.");

    private final String description;
}