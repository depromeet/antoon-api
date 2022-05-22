package kr.co.antoon.webtoon.converter;

import kr.co.antoon.common.Utility;
import kr.co.antoon.graph.domain.GraphScoreSnapshot;
import kr.co.antoon.webtoon.domain.Webtoon;
import kr.co.antoon.webtoon.dto.WebtoonDto;
import kr.co.antoon.webtoon.dto.WebtoonNativeDto;
import kr.co.antoon.webtoon.dto.response.WebtoonDayResponse;
import kr.co.antoon.webtoon.dto.response.WebtoonGenreResponse;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WebtoonConverter {
    public static WebtoonDto toWebtoonDto(List<WebtoonNativeDto> webtoon) {
        Set<WebtoonDto.GenreDto> genres = new HashSet<>();
        Set<WebtoonDto.PublishDayDto> days = new HashSet<>();
        Set<WebtoonDto.WriterDto> writers = new HashSet<>();

        webtoon.forEach(dto -> {
            genres.add(new WebtoonDto.GenreDto(
                    dto.getWebtoonGenreId(),
                    dto.getGenreCategory(),
                    dto.getGenreCategory().getDescription()
            ));
            days.add(new WebtoonDto.PublishDayDto(
                    dto.getWebtoonPublishDayId(),
                    dto.getDay()
            ));
            writers.add(new WebtoonDto.WriterDto(
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
                writers
        );
    }
}