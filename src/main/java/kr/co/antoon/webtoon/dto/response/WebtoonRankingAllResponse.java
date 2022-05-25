package kr.co.antoon.webtoon.dto.response;

import kr.co.antoon.graph.domain.GraphScoreSnapshot;
import kr.co.antoon.webtoon.domain.Webtoon;
import kr.co.antoon.webtoon.domain.vo.Platform;

import java.time.LocalDateTime;
import java.util.List;

public record WebtoonRankingAllResponse(
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
    ) {
        public WebtoonRankingResponse(Webtoon webtoon, GraphScoreSnapshot graphScoreSnapshot) {
            this(
                    webtoon.getId(),
                    webtoon.getWebtoonUrl(),
                    webtoon.getThumbnail(),
                    webtoon.getTitle(),
                    graphScoreSnapshot.getGraphScore(),
                    graphScoreSnapshot.getScoreGapPercent(),
                    graphScoreSnapshot.getSnapshotTime(),
                    webtoon.getStatus().getDescription(),
                    webtoon.getPlatform()
            );
        }
    }
}