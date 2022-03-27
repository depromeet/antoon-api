package kr.co.toonzip.error.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorMessage {
    CONFLICT_ERROR("예기치 못한 에러가 발생했습니다.");

    private final String description;
}