package kr.co.antoon.webtoon.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.co.antoon.webtoon.domain.vo.ActiveStatus;
import kr.co.antoon.webtoon.domain.vo.GenreCategory;
import kr.co.antoon.webtoon.domain.vo.Platform;

import java.util.List;

@Schema(description = "Webtoon 상세 DTO")
public record WebtoonDto(
        Long webtoondId,
        String title,
        String content,
        String webtoonUrl,
        String thumbnail,
        Platform platform,
        String platformDescription,
        ActiveStatus status,
        String statusDescription,
        List<GenreDto> genres,
        List<PublishDayDto> publishDays,
        List<WriterDto> writers
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
}