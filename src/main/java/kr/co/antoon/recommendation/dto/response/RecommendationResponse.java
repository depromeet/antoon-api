package kr.co.antoon.recommendation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.co.antoon.recommendation.domain.RecommendationCount;

public record RecommendationResponse(
        @Schema(description = "탑승 인원수")
        int joinCount,
        @Schema(description = "하차 인원수")
        int leaveCount,
        @Schema(description = "탑승 여부")
        boolean isJoined,
        @Schema(description = "하차 여부")
        boolean isLeaved
) {
    public RecommendationResponse(
            RecommendationCount recommendationCount,
            boolean isJoined,
            boolean isLeaved
    ) {
        this(
                recommendationCount.getJoinCount(),
                recommendationCount.getLeaveCount(),
                isJoined,
                isLeaved
        );
    }
}
