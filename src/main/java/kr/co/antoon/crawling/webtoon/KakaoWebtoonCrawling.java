package kr.co.antoon.crawling.webtoon;

import kr.co.antoon.webtoon.dto.WebtoonCrawlingDto;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
public class KakaoWebtoonCrawling implements WebtoonCrawling {

    public static final String KAKAO_WEBTOON = "KakaoWebtoon";
    public static final String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/99.0.4844.84 Safari/537.36";

    @Override
    public WebtoonCrawlingDto crawling() {
        var bundle = new ArrayList<WebtoonCrawlingDto.WebtoonCrawlingDetail>();

        try {
            var kakaoWebtoonDocument = Jsoup.connect("https://page.kakao.com/main?categoryUid=10&subCategoryUid=10000").get();
            var contentListElements = kakaoWebtoonDocument.select("div.css-19y0ur2");

            for (Element contentElement : contentListElements) {
                var aElements = contentElement.select("a");
                for (Element aElement : aElements) {
                    var thumbnail = "https:" + aElement.select("img").attr("data-src");
                    var score = convertRankingToScore(aElement.select("div.css-nfxgqr").text());
                    var url = "https://page.kakao.com" + aElement.attr("href");
                    var detailPageDocument = Jsoup.connect(url).get();
                    var innerElements = detailPageDocument.select("div.css-1ydjg2i");
                    innerElements.forEach(innerElement -> {
                        var title = innerElement.select("h2.text-ellipsis.css-jgjrt").text();
                        var dayInfoBox = Objects.requireNonNull(innerElement.select("div.css-ymlwac").first()).child(1).text().split("\\|");
                        var day = dayInfoBox[0].substring(0, 1);
                        var writer = Objects.requireNonNull(innerElement.select("div.css-ymlwac").first()).child(2).text();
                        var content = "";
                        var genre = "";
                        var genres = new ArrayList<String>();

                        JSONObject seriesDetail = getJsonObject(url);
                        try {
                            content = (String) seriesDetail.get("description");
                            genre = ((String) seriesDetail.get("sub_category")).replaceAll("만화", "");
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
                        log.info("[Kakao Webtoon Crawling] title-> {} / score -> {}", title, score);
                    });
                }
            }
        } catch (Exception e) {
            log.error("[Kakao Webtoon Crawling Error] {}", e.getCause());
        }
        return new WebtoonCrawlingDto(bundle);
    }

    private JSONObject getJsonObject(String url) {
        var eachUrlArr = url.split("=");
        var seriesId = eachUrlArr[eachUrlArr.length - 1];
        var popupModalPageUrl = "https://api2-page.kakao.com/api/v4/store/seriesdetail?seriesid=" + seriesId;

        JSONObject seriesDetail = null;
        seriesDetail = getSeriesDetail(popupModalPageUrl, seriesDetail);
        return seriesDetail;
    }

    private JSONObject getSeriesDetail(String popupModalPageUrl, JSONObject seriesDetail) {
        try {
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
        } catch (IOException | JSONException e) {
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
        }
        return score;
    }
}
