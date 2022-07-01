package kr.co.antoon.webtoon.dto.response;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import kr.co.antoon.graph.domain.GraphScoreSnapshot;
import kr.co.antoon.webtoon.domain.Webtoon;
import kr.co.antoon.webtoon.domain.vo.Platform;

import java.util.List;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "@class")
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
            String snapshotTime,
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
                    graphScoreSnapshot.getSnapshotTime().toString(),
                    webtoon.getStatus().getDescription(),
                    webtoon.getPlatform()
            );
        }
    }
}
