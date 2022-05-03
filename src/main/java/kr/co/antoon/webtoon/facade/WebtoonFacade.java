package kr.co.antoon.webtoon.facade;

import kr.co.antoon.webtoon.application.WebtoonGenreService;
import kr.co.antoon.webtoon.application.WebtoonService;
import kr.co.antoon.webtoon.application.WebtoonWriterService;
import kr.co.antoon.webtoon.dto.response.WebtoonDetailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static kr.co.antoon.webtoon.converter.WebtoonConverter.toWebtoonDetailDto;

@Component
@RequiredArgsConstructor
public class WebtoonFacade {
    private final WebtoonService webtoonService;
    private final WebtoonWriterService webtoonWriterService;
    private final WebtoonGenreService webtoonGenreService;

    @Transactional(readOnly = true)
    public WebtoonDetailResponse getWebtoon(Long id) {
        var webtoon = webtoonService.findById(id);
        var writer = webtoonWriterService.findNameByWebtoonId(id);
        var category = webtoonGenreService.findCategoryByWebtoonId(id);

        return toWebtoonDetailDto(webtoon, writer, category);
    }
}