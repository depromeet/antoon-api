package kr.co.antoon.webtoon.facade;

import kr.co.antoon.webtoon.application.WebtoonGenreService;
import kr.co.antoon.webtoon.application.WebtoonPublishDayService;
import kr.co.antoon.webtoon.application.WebtoonService;
import kr.co.antoon.webtoon.application.WebtoonWriterService;
import kr.co.antoon.webtoon.dto.response.WebtoonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class WebtoonFacade {
    private final WebtoonService webtoonService;
    private final WebtoonWriterService webtoonWriterService;
    private final WebtoonGenreService webtoonGenreService;
    private final WebtoonPublishDayService webtoonPublishDayService;

    @Transactional(readOnly = true)
    public WebtoonResponse getWebtoon(Long id) {
        var webtoon = webtoonService.findById(id);
        var writer = webtoonWriterService.findNameByWebtoonId(id);
        var category = webtoonGenreService.findCategoryByWebtoonId(id);
        var days = webtoonPublishDayService.findDaysByWebtoonId(id);

        return new WebtoonResponse(webtoon, writer, category, days);
    }
}