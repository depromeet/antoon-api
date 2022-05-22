package kr.co.antoon.graph.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.co.antoon.graph.domain.GraphScoreSnapshot;
import kr.co.antoon.webtoon.domain.vo.ActiveStatus;
import kr.co.antoon.webtoon.domain.vo.Platform;
import kr.co.antoon.webtoon.dto.WebtoonDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Set;

@Getter
@RequiredArgsConstructor
public class TopRankResponse {
    private final List<TopRankWebtooon> webtoons;

    @Getter
    @Schema(description = "Top Rank Response")
    public static class TopRankWebtooon {
        @Schema(description = "순위")
        private final Integer rank;
        @Schema(description = "웹툰 점수")
        private final int score;
        @Schema(description = "gap percent")
        private final double gapPercent;
        @Schema(description = "웹툰 id")
        private final Long id;
        @Schema(description = "제목")
        private final String title;
        @Schema(description = "소개")
        private final String content;
        @Schema(description = "작가")
        private final Set<WebtoonDto.WriterDto> writers;
        @Schema(description = "웹툰 url")
        private final String url;
        @Schema(description = "썸네일")
        private final String thumbnail;
        @Schema(description = "웹툰 상태")
        private final ActiveStatus activeStatus;
        @Schema(description = "웹툰 상태 설명")
        private final String activeStatusDescription;
        @Schema(description = "웹툰 플랫폼")
        private final Platform platform;
        @Schema(description = "웹툰 플랫폼 설명")
        private final String platformDescription;
        @Schema(description = "웹툰 정르")
        private final Set<WebtoonDto.GenreDto> genres;
        @Schema(description = "웹툰 연재요일")
        private final Set<WebtoonDto.PublishDayDto> days;

        public TopRankWebtooon(
                Integer rank,
                GraphScoreSnapshot graphScoreSnapshot,
                WebtoonDto dto
        ) {
            this.rank = rank;
            this.score = graphScoreSnapshot.getGraphScore();
            this.gapPercent = graphScoreSnapshot.getScoreGapPercent();
            this.id = graphScoreSnapshot.getWebtoonId();
            this.title = dto.title();
            this.content = dto.content();
            this.writers = dto.writers();
            this.url = dto.webtoonUrl();
            this.thumbnail = dto.thumbnail();
            this.activeStatus = dto.status();
            this.activeStatusDescription = dto.statusDescription();
            this.platform = dto.platform();
            this.platformDescription = dto.platformDescription();
            this.genres = dto.genres();
            this.days = dto.publishDays();
        }
    }
}
