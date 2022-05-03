package kr.co.antoon.webtoon.converter;

import kr.co.antoon.webtoon.domain.Webtoon;
import kr.co.antoon.webtoon.domain.vo.Category;
import kr.co.antoon.webtoon.dto.response.WebtoonDetailResponse;

import java.util.List;

public class WebtoonConverter {
    public static WebtoonDetailResponse toWebtoonDetailDto(Webtoon webtoon, List<String> writer, List<Category> genre) {
        return WebtoonDetailResponse.builder()
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