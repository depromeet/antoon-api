package kr.co.antoon.webtoon;

import kr.co.antoon.webtoon.domain.Webtoon;
import kr.co.antoon.webtoon.domain.vo.Category;
import kr.co.antoon.webtoon.dto.WebtoonDetailDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class WebtoonConverter {
    public WebtoonDetailDto toWebtoonDetailDto(Webtoon webtoon, List<String> writer, List<Category> genre) {
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