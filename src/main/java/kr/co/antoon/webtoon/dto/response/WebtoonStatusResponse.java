package kr.co.antoon.webtoon.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.co.antoon.webtoon.domain.WebtoonStatusCount;
import kr.co.antoon.webtoon.domain.vo.WebtoonStatusType;

public record WebtoonStatusResponse(
        @Schema(description = "탑승 인원수")
        int joinCount,
        @Schema(description = "하차 인원수")
        int leaveCount,
        @Schema(description = "상하차 상태")
        WebtoonStatusType status,
        @Schema(description = "코인 지급 여부", defaultValue = "false")
        boolean getCoin
) {
    public WebtoonStatusResponse(
            WebtoonStatusCount webtoonStatusCount,
            WebtoonStatusType status
    ) {
        this(
                webtoonStatusCount.getJoinCount(),
                webtoonStatusCount.getLeaveCount(),
                status,
                false
        );
    }

    public WebtoonStatusResponse update(
            boolean getCoin
    ) {
        return new WebtoonStatusResponse(joinCount, leaveCount, status, getCoin);
    }
}
