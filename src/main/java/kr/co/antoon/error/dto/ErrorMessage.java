package kr.co.antoon.error.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorMessage {
    CONFLICT_ERROR("예기치 못한 에러가 발생했습니다."),
    NOT_EXISTS_WEBTOON_ERROR("존재하지 않는 웹툰입니다."),
    NOT_EXISTS_DISCUSSION_ERROR("존재하지 않는 댓글입니다.");

    private final String description;
}