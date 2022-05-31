package kr.co.antoon.recommendation.domain.vo;

import kr.co.antoon.error.dto.ErrorMessage;
import kr.co.antoon.error.exception.common.NotExistsException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RecommendationStatus {
    JOIN("탑승"),
    JOINED("탑승중"),
    LEAVE("하차"),
    LEAVED("하차중"),
    ;

    private final String description;

    public static RecommendationStatus of(String name) {
        return switch (RecommendationStatus.valueOf(name)) {
            case JOIN -> JOINED;
            case LEAVE ->  LEAVED;
            default -> throw new NotExistsException(ErrorMessage.NOT_EXISTS_RECOMMENDATION_COUNT);
        };
    }
}