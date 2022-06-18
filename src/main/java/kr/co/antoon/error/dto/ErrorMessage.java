package kr.co.antoon.error.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorMessage {
    NOT_EXIST_USER("존재하지 않는 사용자입니다."),
    EXPIRED_TOKEN("해당 토큰은 만료된 토큰입니다."),
    CONFLICT_ERROR("예기치 못한 에러가 발생했습니다."),
    NOT_EXISTS_WEBTOON_ERROR("존재하지 않는 웹툰입니다."),
    NOT_EXISTS_DISCUSSION_ERROR("존재하지 않는 댓글입니다."),
    NOT_EXISTS_WEBTOON_PLATFORM_TYPE_ERROR("존재하지 않는 웹툰 플랫폼입니다."),
    ALREADY_JOINED_ERROR("이미 탑승 중입니다."),
    ALREADY_LEAVED_ERROR("이미 하차 중입니다"),
    NOT_VALIDATE_TOKEN("유효하지 않은 토큰입니다."),
    NOT_EXISTS_RECOMMENDATION_COUNT("존재하지 않는 상하차 정보입니다."),
    NOT_EXISTS_GRAPH_SCORE_ERROR("존재하지 않는 그래프 스코어 스냅샷입니다."),
    NOT_EXISTS_OAUTH_INFO("존재하지 않는 OAUTH 계정입니다."),
    NOT_EXISTS_PERIOD_TYPE("존재하지 않는 기간 필터링 타입니다."),
    NOT_VALID_ROLE_ERROR("유효하지 않은 권한입니다."),
    NOT_EXISTS_CHARACTER("존재하지 않는 인물입니다."),
    ;

    private final String description;
}