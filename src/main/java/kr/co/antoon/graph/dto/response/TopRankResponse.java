package kr.co.antoon.graph.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.co.antoon.graph.domain.GraphScoreSnapshot;
import kr.co.antoon.webtoon.domain.vo.ActiveStatus;
import kr.co.antoon.webtoon.domain.vo.Platform;
import kr.co.antoon.webtoon.dto.WebtoonDto;

import java.util.List;
import java.util.Set;

public record TopRankResponse(
        List<TopRankWebtooon> webtoons
) {
    @Schema(description = "Top Rank Response")
    public record TopRankWebtooon(
            @Schema(description = "순위")
            Integer rank,
            @Schema(description = "웹툰 점수")
            int score,
            @Schema(description = "gap percent")
            double gapPercent,
            @Schema(description = "웹툰 id")
            Long id,
            @Schema(description = "제목")
            String title,
            @Schema(description = "소개")
            String content,
            @Schema(description = "작가")
            Set<WebtoonDto.WriterDto> writers,
            @Schema(description = "웹툰 url")
            String url,
            @Schema(description = "썸네일")
            String thumbnail,
            @Schema(description = "웹툰 상태")
            ActiveStatus activeStatus,
            @Schema(description = "웹툰 상태 설명")
            String activeStatusDescription,
            @Schema(description = "웹툰 플랫폼")
            Platform platform,
            @Schema(description = "웹툰 플랫폼 설명")
            String platformDescription,
            @Schema(description = "웹툰 정르")
            Set<WebtoonDto.GenreDto> genres,
            @Schema(description = "웹툰 연재요일")
            Set<WebtoonDto.PublishDayDto> days
    ) {
        public TopRankWebtooon(
                Integer rank,
                GraphScoreSnapshot graphScoreSnapshot,
                WebtoonDto dto
        ) {
            this(
                    rank,
                    graphScoreSnapshot.getGraphScore(),
                    graphScoreSnapshot.getScoreGapPercent(),
                    graphScoreSnapshot.getWebtoonId(),
                    dto.title(),
                    dto.content(),
                    dto.writers(),
                    dto.webtoonUrl(),
                    dto.thumbnail(),
                    dto.status(),
                    dto.statusDescription(),
                    dto.platform(),
                    dto.platformDescription(),
                    dto.genres(),
                    dto.publishDays()
            );
        }
    }
}
