package kr.co.antoon.webtoon.domain.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum WebtoonStatusType {
    JOIN("탑승"),
    JOINED("탑승중"),
    LEAVE("하차"),
    LEAVED("하차중"),
    NONE("없음"),
    ;

    private final String description;

    public static WebtoonStatusType of(WebtoonStatusType status) {
        return switch (status) {
            case JOIN -> JOINED;
            case LEAVE -> LEAVED;
            default -> NONE;
        };
    }
}
