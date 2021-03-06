package kr.co.antoon.webtoon.dto;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import kr.co.antoon.recommendation.domain.vo.RecommendationStatus;
import kr.co.antoon.webtoon.domain.vo.ActiveStatus;
import kr.co.antoon.webtoon.domain.vo.GenreCategory;
import kr.co.antoon.webtoon.domain.vo.Platform;

import java.util.Set;

@Schema(description = "Webtoon 상세 DTO")
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "@class")
public record WebtoonDto(
        Long webtoonId,
        String title,
        String content,
        String webtoonUrl,
        String thumbnail,
        Platform platform,
        String platformDescription,
        ActiveStatus status,
        String statusDescription,
        Set<GenreDto> genres,
        Set<PublishDayDto> publishDays,
        Set<WriterDto> writers,
        Long recommendationCountId,
        Integer joinCount,
        Integer leaveCount,
        int score,
        int scoreGap,
        double scoreGapPercent,
        RecommendationStatus recommendationStatus,
        Integer ranking,
        Set<CharacterDto> characters
) {
    public record GenreDto(
            Long webtoonGenreId,
            GenreCategory genreCategory,
            String genreCategoryDescription
    ) { }

    public record PublishDayDto(
            Long webtoonPublishDayId,
            String day
    ) { }

    public record WriterDto(
            Long webtoonWriterId,
            String name
    ) { }

    public record CharacterDto(
            Long characterId,
            String name,
            String imageUrl
    ) { }
}
