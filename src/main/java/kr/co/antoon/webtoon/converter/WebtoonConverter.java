package kr.co.antoon.webtoon.converter;

import kr.co.antoon.recommendation.domain.vo.RecommendationStatus;
import kr.co.antoon.webtoon.domain.Webtoon;
import kr.co.antoon.webtoon.domain.vo.Platform;
import kr.co.antoon.webtoon.dto.WebtoonDto;
import kr.co.antoon.webtoon.dto.query.WebtoonNativeDto;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static kr.co.antoon.crawling.dto.WebtoonCrawlingDto.WebtoonCrawlingDetail;
import static kr.co.antoon.criteria.BasicAllocateScore.getDifferencePercentage;
import static kr.co.antoon.webtoon.dto.WebtoonDto.GenreDto;
import static kr.co.antoon.webtoon.dto.WebtoonDto.PublishDayDto;
import static kr.co.antoon.webtoon.dto.WebtoonDto.WriterDto;

public class WebtoonConverter {
    public static WebtoonDto toWebtoonDto(List<WebtoonNativeDto> webtoon) {
        Set<GenreDto> genres = new HashSet<>();
        Set<PublishDayDto> days = new HashSet<>();
        Set<WriterDto> writers = new HashSet<>();

        webtoon.forEach(dto -> {
            genres.add(new GenreDto(
                    dto.getWebtoonGenreId(),
                    dto.getGenreCategory(),
                    dto.getGenreCategory().getDescription()
            ));
            days.add(new PublishDayDto(
                    dto.getWebtoonPublishDayId(),
                    dto.getDay()
            ));
            writers.add(new WriterDto(
                    dto.getWebtoonWriterId(),
                    dto.getName()
            ));
        });

        return new WebtoonDto(
                webtoon.get(0).getWebtoonId(),
                webtoon.get(0).getTitle(),
                webtoon.get(0).getContent(),
                webtoon.get(0).getWebtoonUrl(),
                webtoon.get(0).getThumbnail(),
                webtoon.get(0).getPlatform(),
                webtoon.get(0).getPlatform().getDescription(),
                webtoon.get(0).getStatus(),
                webtoon.get(0).getStatus().getDescription(),
                genres,
                days,
                writers,
                webtoon.get(0).getRecommendationCountId(),
                webtoon.get(0).getJoinCount(),
                webtoon.get(0).getLeaveCount(),
                webtoon.get(0).getGraphScore(),
                webtoon.get(0).getScoreGap(),
                getDifferencePercentage(webtoon.get(0).getGraphScore(), webtoon.get(0).getScoreGap()),
                status(webtoon.get(0).getRecommendationStatus()),
                webtoon.get(0).getRanking()
        );
    }

    private static RecommendationStatus status(RecommendationStatus status) {
        if (status == null) {
            return RecommendationStatus.NONE;
        }
        return status;
    }

    public static Webtoon toWebtoon(WebtoonCrawlingDetail crawlingWebtton, Platform platform) {
        return Webtoon.builder()
                .title(crawlingWebtton.title())
                .content(crawlingWebtton.content())
                .webtoonUrl(crawlingWebtton.url())
                .thumbnail(crawlingWebtton.thumbnail())
                .platform(platform)
                .build();
    }
}
