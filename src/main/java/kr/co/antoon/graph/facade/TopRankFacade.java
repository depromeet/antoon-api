package kr.co.antoon.graph.facade;

import kr.co.antoon.graph.application.TopRankService;
import kr.co.antoon.graph.dto.TopRankResponse;
import kr.co.antoon.webtoon.application.WebtoonGenreService;
import kr.co.antoon.webtoon.application.WebtoonPublishDayService;
import kr.co.antoon.webtoon.application.WebtoonService;
import kr.co.antoon.webtoon.application.WebtoonWriterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TopRankFacade {
    private final TopRankService topRankService;
    private final WebtoonService webtoonService;
    private final WebtoonWriterService webtoonWriterService;
    private final WebtoonGenreService webtoonGenreService;
    private final WebtoonPublishDayService webtoonPublishDayService;

    @Transactional(readOnly = true)
    public TopRankResponse findTopRank() {
        return new TopRankResponse(topRankService.findTopRank()
                .stream()
                .map(tr -> new TopRankResponse.TopRankWebtooon(
                        tr.getRanking(),
                        webtoonService.findById(tr.getWebtoonId()),
                        webtoonWriterService.findNameByWebtoonId(tr.getWebtoonId()),
                        webtoonGenreService.findCategoryByWebtoonId(tr.getWebtoonId()),
                        webtoonPublishDayService.findDaysByWebtoonId(tr.getWebtoonId())
                )).collect(Collectors.toList()));
    }
}