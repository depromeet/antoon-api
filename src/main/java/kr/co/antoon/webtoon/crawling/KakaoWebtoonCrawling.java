package kr.co.antoon.webtoon.crawling;

import kr.co.antoon.webtoon.dto.WebtoonCrawlingDto;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Component(KakaoWebtoonCrawling.KAKAO_WEBTOON)
public class KakaoWebtoonCrawling implements WebtoonCrawling {

    public static final String KAKAO_WEBTOON = "KakaoWebtoon";
    public static final String KAKAO_WEBTOON_URL = "https://page.kakao.com/main?categoryUid=10&subCategoryUid=10000";
    public static final String KAKAO_WEBTOON_DOMAIN = "https://page.kakao.com";
    public static final String HTTPS = "https:";
    public static final String WEBTOON_RANKING_1 = "1위";
    public static final String WEBTOON_RANKING_2 = "2위";
    public static final String WEBTOON_RANKING_3 = "3위";
    public static final String WEBTOON_RANKING_4 = "4위";
    public static final String WEBTOON_RANKING_5 = "5위";

    @Override
    public WebtoonCrawlingDto crawling() {

        var bundle = new ArrayList<WebtoonCrawlingDto.WebtoonCrawlingDetail>();

        try {
            var kakaoWebtoonDocument = Jsoup.connect(KAKAO_WEBTOON_URL).get();
            var contentListElements = kakaoWebtoonDocument.select("div.css-19y0ur2");

            for (Element contentElement : contentListElements) {
                var aLinkElements = contentElement.select("a");

                for (Element aLinkElement : aLinkElements) {
                    var thumbnail = HTTPS + aLinkElement.select("img").attr("data-src");
                    var score = convertRankingToScore(aLinkElement.select("div.css-nfxgqr").text());
                    var url = KAKAO_WEBTOON_DOMAIN + aLinkElement.attr("href");
                    var webtoonDetailDocument = Jsoup.connect(url).get();

                    var innerElements = webtoonDetailDocument.select("div.css-1ydjg2i");
                    innerElements.forEach(innerElement -> {
                        var title = innerElement.select("h2.text-ellipsis.css-jgjrt").text();
                        var dayInfoBox = Objects.requireNonNull(innerElement.select("div.css-ymlwac").first()).child(1).text().split("\\|");
                        var day = dayInfoBox[0];
                        var writer = Objects.requireNonNull(innerElement.select("div.css-ymlwac").first()).child(2).text().split(",");

                        // TODO: 추가 구현 필요 Content, Genres
                        var content = "";
                        List<String> genres = new ArrayList<>();
                        genres.add("공포");
                        genres.add("무협");
                        genres.add("판타지");

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
