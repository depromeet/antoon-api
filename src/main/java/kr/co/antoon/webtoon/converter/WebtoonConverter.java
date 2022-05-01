package kr.co.antoon.webtoon.converter;

import kr.co.antoon.webtoon.domain.Webtoon;
import kr.co.antoon.webtoon.domain.vo.Category;
import kr.co.antoon.webtoon.dto.WebtoonDetailDto;

import java.util.List;

public class WebtoonConverter {
    public static WebtoonDetailDto toWebtoonDetailDto(Webtoon webtoon, List<String> writer, List<Category> genre) {
        return WebtoonDetailDto.builder()
                .title(webtoon.getTitle())
                .content(webtoon.getContent())
                .writer(writer)
                .url(webtoon.getUrl())
                .thumbnail(webtoon.getThumbnail())
                .genre(genre)
                .status(webtoon.getStatus())
                .platform(webtoon.getPlatform())
                .build();
    }
}