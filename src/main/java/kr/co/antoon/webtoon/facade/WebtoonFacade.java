package kr.co.antoon.webtoon.facade;

import kr.co.antoon.webtoon.WebtoonConverter;
import kr.co.antoon.webtoon.application.WebtoonGenreService;
import kr.co.antoon.webtoon.application.WebtoonService;
import kr.co.antoon.webtoon.application.WebtoonWriterService;
import kr.co.antoon.webtoon.domain.Webtoon;
import kr.co.antoon.webtoon.domain.vo.Category;
import kr.co.antoon.webtoon.dto.WebtoonDetailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class WebtoonFacade {
    private final WebtoonService webtoonService;
    private final WebtoonWriterService webtoonWriterService;
    private final WebtoonGenreService webtoonGenreService;
    private final WebtoonConverter webtoonConverter;

    @Transactional(readOnly = true)
    public WebtoonDetailDto get(Long id) {
        return webtoonConverter.toWebtoonDetailDto(
                webtoonService.findById(id),
                webtoonWriterService.findNameByWebtoonId(id),
                webtoonGenreService.findCategoryByWebtoonId(id)
        );
    }
}