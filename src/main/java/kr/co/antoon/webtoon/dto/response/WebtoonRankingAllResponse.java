package kr.co.antoon.webtoon.dto.response;

import kr.co.antoon.webtoon.domain.vo.Platform;

import java.time.LocalDateTime;
import java.util.List;

public record WebtoonRankingAllResponse (
        List<WebtoonRankingResponse> webtoons
) {
    public record WebtoonRankingResponse(
            Long id,
            String url,
            String thumbnail,
            String title,
            int score,
            double scoreGapPercent,
            LocalDateTime snapshotTime,
            String activeStatus,
            Platform platform
    ) { }
}
