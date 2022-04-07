package kr.co.toonzip.webtoon.crawling;

import kr.co.toonzip.webtoon.dto.WebtoonCrawlingDto;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;

@Slf4j
@Component
public class NaverWebtoonCrawling implements WebtoonCrawling {

    @Override
    public WebtoonCrawlingDto crawling() {

        var bundle = new ArrayList<WebtoonCrawlingDto.WebtoonCrawlingDetail>();

        try {
            var naverWeekendWebtoonDocument = Jsoup.connect(WebtoonCrawlingValue.NAVER_WEEKEND_URL).get();
            var webtoonElements = naverWeekendWebtoonDocument.select(WebtoonCrawlingValue.NAVER_WEEKEND_ELEMENTS);

            for (var weekendWebtoons : webtoonElements) {
                var eachUrlElements = weekendWebtoons.select(WebtoonCrawlingValue.NAVER_ALL_URLS);

                for (Element webtoon : eachUrlElements) {
                    var url = WebtoonCrawlingValue.NAVER_WEBTOON_DOMIN + webtoon.attr(WebtoonCrawlingValue.NAVER_WEBTOON_URL_ATTRIBUTE_KEY);
                    var day = weekendWebtoons.getElementsByTag(WebtoonCrawlingValue.NAVER_WEBTOON_DAT_ATTRIBUTE_KEY).text().substring(0, 1);

                    var webtoonDetailDocument = Jsoup.connect(url).get();

                    var score = webtoonDetailDocument.select(WebtoonCrawlingValue.NAVER_WEBTTON_SCORE[0])
                            .get(0)
                            .getElementsByTag(WebtoonCrawlingValue.NAVER_WEBTTON_SCORE[1])
                            .text();

                    var innerElements = webtoonDetailDocument.select(WebtoonCrawlingValue.NAVER_INNSER_ELEMENTS);
                    for (var innerElement : innerElements) {
                        var title = innerElement.select(WebtoonCrawlingValue.NAVER_WEBTOON_TITLE).text();
                        var content = innerElement.getElementsByTag(WebtoonCrawlingValue.NAVER_WEBTOON_CONTENT).get(0).text();
                        var writer = innerElement.select(WebtoonCrawlingValue.NAVER_WEBTOON_WRITER).text();
                        var thumbnail = innerElement.select(WebtoonCrawlingValue.NAVER_WEBTOON_THUMBAIL[0]).attr(WebtoonCrawlingValue.NAVER_WEBTOON_THUMBAIL[1]);
                        var genre = innerElement.select(WebtoonCrawlingValue.NAVER_WEBTOON_GENRE).text();

                        bundle.add(WebtoonCrawlingDto.WebtoonCrawlingDetail.builder()
                                .title(title)
                                .content(content)
                                .writer(writer)
                                .thumbnail(thumbnail)
                                .genre(genre)
                                .url(url)
                                .score(Double.parseDouble(score))
                                .day(day)
                                .build());

                        log.info("[Naver Webtoon Crawling] url-> {} / title -> {}", url, title);
                    }
                }
            }
        } catch (IOException e) {
            log.error("[Naver Webtoon Crawling Error] {}", e.getCause());
        }

        return new WebtoonCrawlingDto(bundle);
    }
}