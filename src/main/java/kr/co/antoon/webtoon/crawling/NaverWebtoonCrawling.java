package kr.co.antoon.webtoon.crawling;

import kr.co.antoon.webtoon.dto.WebtoonCrawlingDto;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
                    innerElements.forEach(innerElement -> {
                        var title = innerElement.select(WebtoonCrawlingValue.NAVER_WEBTOON_TITLE).text();
                        var content = innerElement.getElementsByTag(WebtoonCrawlingValue.NAVER_WEBTOON_CONTENT).get(0).text();
                        var writer = innerElement.select(WebtoonCrawlingValue.NAVER_WEBTOON_WRITER).text().split(" / ");
                        var thumbnail = innerElement.select(WebtoonCrawlingValue.NAVER_WEBTOON_THUMBAIL[0]).attr(WebtoonCrawlingValue.NAVER_WEBTOON_THUMBAIL[1]);
                        var genres = innerElement.select(WebtoonCrawlingValue.NAVER_WEBTOON_GENRE).text().split(",");

                        bundle.add(new WebtoonCrawlingDto.WebtoonCrawlingDetail(
                                title,
                                content,
                                List.of(writer),
                                url,
                                thumbnail,
                                Arrays.stream(genres)
                                        .map(genre -> genre.replace(" ", ""))
                                        .collect(Collectors.toList()),
                                Double.parseDouble(score),
                                day
                        ));

                        log.info("[Naver Webtoon Crawling] title-> {} / url -> {}", title, url);
                    });
                }
            }
        } catch (IOException e) {
            // TODO : Webhook Salck Cruiser
            log.error("[Naver Webtoon Crawling Error] {}", e.getCause());
        }

        return new WebtoonCrawlingDto(bundle);
    }
}