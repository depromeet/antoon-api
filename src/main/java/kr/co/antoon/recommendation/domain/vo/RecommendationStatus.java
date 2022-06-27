package kr.co.antoon.recommendation.domain.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RecommendationStatus {
    JOIN("탑승"),
    JOINED("탑승중"),
    LEAVE("하차"),
    LEAVED("하차중"),
    NONE("없음"),
    ;

    private final String description;

    public static RecommendationStatus of(RecommendationStatus status) {
        return switch (status) {
            case JOIN -> JOINED;
            case LEAVE -> LEAVED;
            default -> NONE;
        };
    }
}
