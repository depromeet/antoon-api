package kr.co.antoon.webtoon.crawling;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Connection;
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
                    var day = dayInfoBox[0].substring(0,1);
                    var writer = innerElement.select("div.css-ymlwac").first().child(2).text().split(",");

                    var eachUrlArr = url.split("=");
                    var seriesId = eachUrlArr[eachUrlArr.length - 1];
                    var content = "";
                    var genre = "";
                    JSONObject seriseDetail = getModalPopupData(seriesId);
                    if (seriseDetail != null) {
                        try {
                            content = (String) seriseDetail.get("description");
                            genre = ((String) seriseDetail.get("sub_category")).replaceAll("만화", "");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    log.info("============= [Kakao Webtoon Crawling] ===============");
                    log.info("title = {}", title);
                    log.info("content = {}", content);
                    log.info("url = {}", url);
                    log.info("thumbnail = {}", thumbnail);
                    log.info("genre = {}", genre);
                    log.info("score = {}", score);
                    log.info("day = {}", day);
                    log.info("=====================================");
                });
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private JSONObject getModalPopupData(String seriesId) {
        JSONObject seriesDetail = null;
        var popupModalPageUrl = "https://api2-page.kakao.com/api/v4/store/seriesdetail?seriesid=" + seriesId;

        try {
            String detailPagePopupModalUrl = "https://api2-page.kakao.com/api/v4/store/seriesdetail?seriesid=" + seriesId;
            Connection.Response response = Jsoup.connect(popupModalPageUrl)
                    .header("Accept", "application/json")
                    .header("Accept-Encoding", "gzip, deflate, br")
                    .header("Accept-Language", "ko,ko-KR;q=0.9,en-US;q=0.8,en;q=0.7")
                    .header("Cache-Control", "no-cache")
                    .header("Connection", "keep-alive")
                    .header("Content-Length", "0")
                    .header("Host", "api2-page.kakao.com")
                    .header("Pragma", "no-cache")
                    .header("Referer", "https://page.kakao.com/")
                    .method(Connection.Method.POST)
                    .userAgent(USER_AGENT)
                    .ignoreContentType(true)
                    .execute();

            String body = response.body();
            JSONObject jsonObject = new JSONObject(body);
            seriesDetail = (JSONObject) jsonObject.get("seriesdetail");

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return seriesDetail;
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
