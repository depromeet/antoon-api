package kr.co.toonzip.webtoon.facade;

import kr.co.toonzip.webtoon.application.WebtoonPublishDayService;
import kr.co.toonzip.webtoon.application.WebtoonService;
import kr.co.toonzip.webtoon.application.WebtoonSnapshotService;
import kr.co.toonzip.webtoon.crawling.WebtoonCrawling;
import kr.co.toonzip.webtoon.domain.Webtoon;
import kr.co.toonzip.webtoon.domain.WebtoonPublishDay;
import kr.co.toonzip.webtoon.domain.WebtoonSnapshot;
import kr.co.toonzip.webtoon.domain.vo.Platform;
import kr.co.toonzip.webtoon.dto.WebtoonCrawlingBundle;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class WebtoonCrawlingFacade {
    private final WebtoonService webtoonService;
    private final WebtoonPublishDayService webtoonPublishDayService;
    private final WebtoonSnapshotService webtoonSnapshotService;
    private final WebtoonCrawling webtoonCrawling;

    @Transactional
    public void crawling() {
        var webtoonList = webtoonService.findAll();
        var crawling = webtoonCrawling.crawling();

        Long save = null;

        for (var detail : crawling.getBundle()) {

            boolean check = true;
            for (var webtoon : webtoonList) {
                //
                if (webtoon.getTitle().equals(detail.getTitle())) {
                    webtoon.update(
                            detail.getTitle(),
                            detail.getContent(),
                            detail.getWriter(),
                            detail.getThumbnail(),
                            detail.getUrl(),
                            detail.getGenre()
                    );

                    save = webtoon.getId();
                    check = false;
                    break;
                }
            }

            if (check) {
                var build = Webtoon.builder()
                        .title(detail.getTitle())
                        .content(detail.getContent())
                        .writer(detail.getWriter())
                        .url(detail.getUrl())
                        .thumbnail(detail.getThumbnail())
                        .genre(detail.getGenre())
                        .platform(Platform.NAVER)
                        .build();

                save = webtoonService.save(build);
            }


            var webtoonPublishDay = new WebtoonPublishDay(detail.getDay(), save);
            webtoonPublishDayService.save(webtoonPublishDay);

            var webtoonSnapshot1 = new WebtoonSnapshot(detail.getScore(), save);
            webtoonSnapshotService.save(webtoonSnapshot1);
        }
    }
}