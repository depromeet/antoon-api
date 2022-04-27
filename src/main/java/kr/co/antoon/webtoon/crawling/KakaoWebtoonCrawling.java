package kr.co.antoon.webtoon.crawling;

import kr.co.antoon.webtoon.dto.WebtoonCrawlingDto;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Component(KakaoWebtoonCrawling.KAKAO_WEBTOON)
public class KakaoWebtoonCrawling implements WebtoonCrawling {

    public static final String KAKAO_WEBTOON = "KakaoWebtoon";
    public static final String KAKAO_WEBTOON_DOMAIN = "https://page.kakao.com";
    public static final String KAKAO_WEBTOON_URL = "https://page.kakao.com/main?categoryUid=10&subCategoryUid=10000";
    public static final String POPUP_MODAL_PAGE_URL = "https://api2-page.kakao.com/api/v4/store/seriesdetail?seriesid=";
    public static final String HTTPS = "https:";
    public static final String WEBTOON_RANKING_1 = "1위";
    public static final String WEBTOON_RANKING_2 = "2위";
    public static final String WEBTOON_RANKING_3 = "3위";
    public static final String WEBTOON_RANKING_4 = "4위";
    public static final String WEBTOON_RANKING_5 = "5위";
    public final static String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/99.0.4844.84 Safari/537.36";
    public static final String ACCEPT = "Accept";
    public static final String APPLICATION_JSON = "application/json";
    public static final String ACCEPT_ENCODING = "Accept-Encoding";
    public static final String GZIP_DEFLATE_BR = "gzip, deflate, br";
    public static final String ACCEPT_LANGUAGE = "Accept-Language";
    public static final String ACEEPT_LANGUAGE_VALUE = "ko,ko-KR;q=0.9,en-US;q=0.8,en;q=0.7";
    public static final String CACHE_CONTROL = "Cache-Control";
    public static final String NO_CACHE = "no-cache";
    public static final String CONNECTION = "Connection";
    public static final String KEEP_ALIVE = "keep-alive";
    public static final String CONTENT_LENGTH = "Content-Length";
    public static final String CONTENT_LENGTH_VALUE = "0";
    public static final String HOST = "Host";
    public static final String API_2_PAGE_KAKAO_COM = "api2-page.kakao.com";
    public static final String PRAGMA = "Pragma";
    public static final String REFERER = "Referer";
    public static final String PAGE_KAKAO_COM = "https://page.kakao.com/";


    @Override
    public WebtoonCrawlingDto crawling() {
        var bundle = new ArrayList<WebtoonCrawlingDto.WebtoonCrawlingDetail>();

        try {
            var kakaoWebtoonDocument = Jsoup.connect(KAKAO_WEBTOON_URL).get();
            var contentListElements = kakaoWebtoonDocument.select("div.css-19y0ur2");

            for (Element contentElement : contentListElements) {
                var aElements = contentElement.select("a");

                for (Element aElement : aElements) {
                    var thumbnail = HTTPS + aElement.select("img").attr("data-src");
                    var score = convertRankingToScore(aElement.select("div.css-nfxgqr").text());
                    var url = KAKAO_WEBTOON_DOMAIN + aElement.attr("href");
                    var detailPageDocument = Jsoup.connect(url).get();

                    var innerElements = detailPageDocument.select("div.css-1ydjg2i");
                    innerElements.forEach(innerElement -> {
                        var title = innerElement.select("h2.text-ellipsis.css-jgjrt").text();
                        var dayInfoBox = Objects.requireNonNull(innerElement.select("div.css-ymlwac").first()).child(1).text().split("\\|");
                        var day = dayInfoBox[0];
                        var writer = Objects.requireNonNull(innerElement.select("div.css-ymlwac").first()).child(2).text().split(",");
                        var content = "";
                        var genre = "";
                        var genres = new ArrayList<String>();

                        // Content, Genres는 Modal Page 크롤링
                        JSONObject seriesDetail = getJsonObject(url);
                        try {
                            content = (String) seriesDetail.get("description");
                            genre = (String) seriesDetail.get("sub_category");
                            genres.add(genre);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        bundle.add(new WebtoonCrawlingDto.WebtoonCrawlingDetail(
                                        title,
                                        content,
                                        List.of(writer),
                                        url,
                                        thumbnail,
                                        genres,
                                        Double.parseDouble(score),
                                        day
                                )
                        );

                        log.info("[Kakao Webtoon Crawling] title-> {} / url -> {}", title, url);
                    });
                }
            }
        } catch (Exception e) {
            // TODO : Webhook Slack Cruiser
            log.error("[Kakao Webtoon Crawling Error] {}", e.getCause());
        }

        return new WebtoonCrawlingDto(bundle);
    }

    private JSONObject getJsonObject(String url) {
        var eachUrlArr = url.split("=");
        var seriesId = eachUrlArr[eachUrlArr.length - 1];
        var popupModalPageUrl = POPUP_MODAL_PAGE_URL + seriesId;

        JSONObject seriesDetail = null;
        seriesDetail = getSeriesDetail(popupModalPageUrl, seriesDetail);
        return seriesDetail;
    }

    private JSONObject getSeriesDetail(String popupModalPageUrl, JSONObject seriesDetail) {
        try {
            Connection.Response response = Jsoup.connect(popupModalPageUrl)
                    .header(ACCEPT, APPLICATION_JSON)
                    .header(ACCEPT_ENCODING, GZIP_DEFLATE_BR)
                    .header(ACCEPT_LANGUAGE, ACEEPT_LANGUAGE_VALUE)
                    .header(CACHE_CONTROL, NO_CACHE)
                    .header(CONNECTION, KEEP_ALIVE)
                    .header(CONTENT_LENGTH, CONTENT_LENGTH_VALUE)
                    .header(HOST, API_2_PAGE_KAKAO_COM)
                    .header(PRAGMA, NO_CACHE)
                    .header(REFERER, PAGE_KAKAO_COM)
                    .method(Connection.Method.POST)
                    .userAgent(USER_AGENT)
                    .ignoreContentType(true)
                    .execute();

            String body = response.body();
            JSONObject jsonObject = new JSONObject(body);
            seriesDetail = (JSONObject) jsonObject.get("seriesdetail");
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return seriesDetail;
    }

    private String convertRankingToScore(String score) {
        switch (score) {
            case WEBTOON_RANKING_1 -> score = "10.0";
            case WEBTOON_RANKING_2 -> score = "9.99";
            case WEBTOON_RANKING_3 -> score = "9.98";
            case WEBTOON_RANKING_4 -> score = "9.97";
            case WEBTOON_RANKING_5 -> score = "9.96";
            default -> score = "0.0";
        }
        return score;
    }
}
