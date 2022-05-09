package kr.co.antoon.webtoon.facade;

import kr.co.antoon.webtoon.application.WebtoonGenreService;
import kr.co.antoon.webtoon.application.WebtoonPublishDayService;
import kr.co.antoon.webtoon.application.WebtoonService;
import kr.co.antoon.webtoon.application.WebtoonWriterService;
import kr.co.antoon.webtoon.domain.vo.ActiveStatus;
import kr.co.antoon.webtoon.dto.response.WebtoonGenreResponse;
import kr.co.antoon.webtoon.dto.response.WebtoonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

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

    @Transactional(readOnly = true)
    public WebtoonGenreResponse getWebtoonsGenreAndStatus(Pageable pageable, String genre) {
        var webtoons = webtoonService.findWebtoonByGenreAndStatus(pageable, genre, ActiveStatus.PUBLISH);
        return  new WebtoonGenreResponse(
                webtoons.stream()
                        .map(WebtoonGenreResponse.WebtoonDetail::new)
                        .collect(Collectors.toList())
        );
    }
}