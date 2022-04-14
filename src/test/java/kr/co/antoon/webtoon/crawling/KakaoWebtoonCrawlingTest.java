package kr.co.antoon.webtoon.crawling;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

/**
 * Kakao Webtoon Crawling Test
 * title
 * content
 * writer
 * url
 * thumbnail
 * genre
 * score
 * day
 */
@Slf4j
public class KakaoWebtoonCrawlingTest {

    @Test
    @DisplayName("카카오 페이지 크롤링 테스트")
    void webtoonCrawling() {
        Document doc = null;
        String kakaoWebtoonUrl = "https://page.kakao.com/main?categoryUid=10&subCategoryUid=10000";
        try {
            doc = Jsoup.connect(kakaoWebtoonUrl).get();
            Elements contentListElements = doc.select("div.css-19y0ur2");

            for (Element contentElement : contentListElements) {
                Elements aElements = contentElement.select("a");
                for (Element aElement : aElements) {
                    String url = "https://page.kakao.com" + aElement.attr("href");
                    var webtoonDetailDocument = Jsoup.connect(url).get();

                    var innerElements = webtoonDetailDocument.select("div.css-1ydjg2i");
                    innerElements.forEach(innerElement -> {
                    var title = innerElement.select("h2.text-ellipsis.css-jgjrt").text();
                    var dayInfoStr = innerElement.select("div.css-ymlwac").first().child(1).text().split("\\|");
                    var day = dayInfoStr[0];
                    var writer = innerElement.select("div.css-ymlwac").first().child(2).text();
                });
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
