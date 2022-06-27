package kr.co.antoon.recommendation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.co.antoon.recommendation.domain.RecommendationCount;
import kr.co.antoon.recommendation.domain.vo.RecommendationStatus;

public record RecommendationResponse(
        @Schema(description = "탑승 인원수")
        int joinCount,
        @Schema(description = "하차 인원수")
        int leaveCount,
        @Schema(description = "상하차 상태")
        RecommendationStatus status,
        @Schema(description = "코인 지급 여부", defaultValue = "false")
        boolean getCoin
) {
    public RecommendationResponse(
            RecommendationCount recommendationCount,
            RecommendationStatus status
    ) {
        this(
                recommendationCount.getJoinCount(),
                recommendationCount.getLeaveCount(),
                status,
                false
        );
    }

    public RecommendationResponse update(
            boolean getCoin
    ) {
        return new RecommendationResponse(joinCount, leaveCount, status, getCoin);
    }
}
