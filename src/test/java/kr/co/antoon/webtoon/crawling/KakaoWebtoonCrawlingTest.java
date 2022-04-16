package kr.co.antoon.webtoon.crawling;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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

    private final static String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/99.0.4844.84 Safari/537.36";

    @Test
    @DisplayName("카카오 페이지 크롤링 테스트")
    void webtoonCrawling() {
        Document doc;
        String kakaoWebtoonUrl = "https://page.kakao.com/main?categoryUid=10&subCategoryUid=10000";
        try {
            doc = Jsoup.connect(kakaoWebtoonUrl).get();
            Elements contentListElements = doc.select("div.css-19y0ur2");

            for (Element contentElement : contentListElements) {
                Elements aElements = contentElement.select("a");

                for (Element aElement : aElements) {
                    var thumbnail = "https:" + aElement.select("img").attr("data-src");
                    var score = convertRankingToScore(aElement.select("div.css-nfxgqr").text());
                    var url = "https://page.kakao.com" + aElement.attr("href");
                    var webtoonDetailDocument = Jsoup.connect(url).get();
                    var innerElements = webtoonDetailDocument.select("div.css-1ydjg2i");
                    innerElements.forEach(innerElement -> {
                    var title = innerElement.select("h2.text-ellipsis.css-jgjrt").text();
                    var dayInfoBox = innerElement.select("div.css-ymlwac").first().child(1).text().split("\\|");
                    var day = dayInfoBox[0];
                    var writer = innerElement.select("div.css-ymlwac").first().child(2).text().split(",");

                    // content, genres
                    // log.debug("[Kakao Webtoon Crawling] -> title={}, writer={}, day={}, score={}, thumbnail={}", title, writer, day, score, thumbnail);
                });
                }
                // getModalPopupData();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getModalPopupData() {
        var popupUrl = "https://api2-page.kakao.com/api/v4/store/seriesdetail?seriesid=54727849";
        Document popupDoc = null;
        try {
            Map map = new HashMap<>();
            map.put("Host", "api2-page.kakao.com");
            map.put("Origin", "https://page.kakao.com");
            map.put("Accept", "application/json");
            map.put("User-Agent", USER_AGENT);
            Connection conn = Jsoup.connect(popupUrl)
                    .headers(map)
                    .userAgent(USER_AGENT)
                    .method(Connection.Method.POST)
                    .ignoreContentType(true);
            popupDoc = conn.get();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String convertRankingToScore(String score) {
        switch (score) {
            case "1위" -> score = "10.0";
            case "2위" -> score = "9.99";
            case "3위" -> score = "9.98";
            case "4위" -> score = "9.97";
            case "5위" -> score = "9.96";
            default -> score = "9.95";
        }
        return score;
    }
}
