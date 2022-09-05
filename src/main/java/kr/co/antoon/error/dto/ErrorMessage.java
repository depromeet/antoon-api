package kr.co.antoon.error.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorMessage {
    /**
     * Common Error Message
     **/
    CONFLICT_ERROR(HttpStatus.BAD_REQUEST, "예기치 못한 에러가 발생했습니다."),

    /**
     * User Error Message
     **/
    NOT_EXIST_USER(HttpStatus.NOT_FOUND, "존재하지 않는 사용자입니다."),
    NOT_VALID_ROLE_ERROR(HttpStatus.FORBIDDEN, "유효하지 않은 권한입니다."),

    /**
     * OAuth Error Message
     **/
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "해당 토큰은 만료된 토큰입니다."),
    NOT_VALIDATE_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다."),
    NOT_EXISTS_OAUTH_INFO(HttpStatus.NOT_FOUND, "존재하지 않는 OAUTH 계정입니다."),

    /**
     * Webtoon Error Message
     **/
    NOT_EXISTS_WEBTOON_ERROR(HttpStatus.NOT_FOUND, "존재하지 않는 웹툰입니다."),

    /**
     * Discussion Error Message
     **/
    NOT_EXISTS_DISCUSSION_ERROR(HttpStatus.NOT_FOUND, "존재하지 않는 댓글입니다."),

    /**
     * Webtoon Status Error Message
     **/
    ALREADY_JOINED_ERROR(HttpStatus.BAD_REQUEST, "이미 탑승 중입니다."),
    ALREADY_LEAVED_ERROR(HttpStatus.CONFLICT, "이미 하차 중입니다"),

    /**
     * Graph Error Message
     **/
    NOT_EXISTS_GRAPH_SCORE_ERROR(HttpStatus.NOT_FOUND, "존재하지 않는 그래프 스코어 스냅샷입니다."),
    NOT_EXISTS_PERIOD_TYPE(HttpStatus.NOT_FOUND, "존재하지 않는 기간 필터링 타입니다."),

    /**
     * Character Error Message
     **/
    NOT_EXISTS_CHARACTER(HttpStatus.NOT_FOUND, "존재하지 않는 인물입니다."),
    NOT_EXISTS_CHARACTER_IMAGE(HttpStatus.NOT_FOUND, "존재하지 않는 인물/커플 이미지입니다."),

    /**
     * Vote Error Message
     **/
    NOT_EXIST_TOPIC(HttpStatus.NOT_FOUND, "존재하지 않는 토픽입니다."),
    NOT_EXISTS_CANDIDATE(HttpStatus.NOT_FOUND, "존재하지 않는 후보입니다."),
    ALREADY_VOTE_ERROR(HttpStatus.CONFLICT, "이미 투표했습니다"),

    /**
     * Aws Error Message
     **/
    FILE_UPLOAD_ERROR(HttpStatus.NOT_FOUND, "오류로 인해 파일 업로드에 실패하였습니다."),

    /**
     * Common Error Message
     **/
    MAPPER_JSON_ERROR(HttpStatus.BAD_REQUEST, "mapper error가 발생했습니다."),

    /**
     * Ant Coin Error Message
     **/
    NOT_EXISTS_ANT_COIN_WALLET(HttpStatus.NOT_FOUND, "안트코인 지갑이 존재하지 않습니다."),
    ;

    private final HttpStatus status;
    private final String description;
}
