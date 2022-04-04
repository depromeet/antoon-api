package kr.co.toonzip.webtoon.crawling;

import kr.co.toonzip.webtoon.dto.WebtoonCrawlingBundle;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;

@Slf4j
@Component
public class NaverWebtoonCrawling implements WebtoonCrawling {

    private final static String NAVER_WEBTOON_URL = "https://comic.naver.com/webtoon/weekday";
    private final static String NAVER_DAILY_WEBTOON_HTML_TREE = "div.list_area.daily_all div.col div.col_inner";
    private final static String NAVER_WEBTOON_DOMAIN = "https://comic.naver.com";
    private final static String NAVER_EACH_HTML_TREE = "ul li div.thumb a";
    private final static String NAVER_EACH_URL_ATTR = "href";

    @Override
    public WebtoonCrawlingBundle crawling() {

        var requests = new ArrayList<WebtoonCrawlingBundle.WebtoonCrawlingDetail>();

        try {
            var naverWebtoonDocument = Jsoup.connect(NAVER_WEBTOON_URL).get();
            var dailyWebtoonElements = naverWebtoonDocument.select(NAVER_DAILY_WEBTOON_HTML_TREE);

            for (var element : dailyWebtoonElements) {
                var eachUrlElements = element.select(NAVER_EACH_HTML_TREE);
                var url = NAVER_WEBTOON_DOMAIN + eachUrlElements.attr(NAVER_EACH_URL_ATTR);

                var day = element.getElementsByTag("h4").text().substring(0, 1);

                var innerDocument = Jsoup.connect(url).get();

                var element1 = innerDocument.select("table.viewList tbody tr").get(2);
                var score = element1.getElementsByTag("strong").text();

                var innerElements = innerDocument.select("div.comicinfo");
                for (var ee : innerElements) {
                    var title = ee.select("span.title").text();
                    var content = ee.getElementsByTag("p").get(0).text();
                    var writer = ee.select("span.wrt_nm").text();
                    var thumbnail = ee.select("div.thumb img").attr("src");
                    var genre = ee.select("span.genre").text();

                    requests.add(WebtoonCrawlingBundle.WebtoonCrawlingDetail.builder()
                            .title(title)
                            .content(content)
                            .writer(writer)
                            .thumbnail(thumbnail)
                            .genre(genre)
                            .url(url)
                            .score(Double.parseDouble(score))
                            .day(day)
                            .build());
                    break;
                }
                break;
            }
        } catch (IOException e) {
            log.error("[Webtoon Crawling Error] Naver Webtoon Error {}", e.getCause());
            e.printStackTrace();
        }

        return new WebtoonCrawlingBundle(requests);
    }

}