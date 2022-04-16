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
@Component(WebtoonCrawlingValue.KAKAO_WEBTOON)
public class KakaoWebtoonCrawling implements WebtoonCrawling {

    @Override
    public WebtoonCrawlingDto crawling() {

        var bundle = new ArrayList<WebtoonCrawlingDto.WebtoonCrawlingDetail>();

        try {
            var kakaoWebtoonDocument = Jsoup.connect(WebtoonCrawlingValue.KAKAO_WEBTOON_URL).get();
            var contentListElements = kakaoWebtoonDocument.select(WebtoonCrawlingValue.KAKAO_CONTENT_ELEMENTS);

            for (Element contentElement : contentListElements) {
                var aLinkElements = contentElement.select(WebtoonCrawlingValue.KAKAO_WEBTOON_LINK_TAG);

                for (Element aLinkElement : aLinkElements) {
                    var thumbnail = WebtoonCrawlingValue.HTTPS + aLinkElement.select(WebtoonCrawlingValue.KAKAO_WEBTOON_IMAGE_TAG).attr(WebtoonCrawlingValue.KAKAO_WEBTOON_IMAGE_ATTRIBUTE_KEY);
                    var score = convertRankingToScore(aLinkElement.select(WebtoonCrawlingValue.KAKAO_WEBTOON_SCORE).text());
                    var url = WebtoonCrawlingValue.KAKAO_WEBTOON_DOMAIN + aLinkElement.attr(WebtoonCrawlingValue.KAKAO_WEBTOON_LINK_ATTRIBUTE_KEY);
                    var webtoonDetailDocument = Jsoup.connect(url).get();

                    var innerElements = webtoonDetailDocument.select(WebtoonCrawlingValue.KAKAO_INNER_ELEMENTS);
                    innerElements.forEach(innerElement -> {
                        var title = innerElement.select(WebtoonCrawlingValue.KAKAO_WEBTOON_TITLE).text();
                        var dayInfoBox = Objects.requireNonNull(innerElement.select(WebtoonCrawlingValue.KAKAO_WEBTOON_DAY_INFO_BOX).first()).child(1).text().split("\\|");
                        var day = dayInfoBox[0];
                        var writer = Objects.requireNonNull(innerElement.select(WebtoonCrawlingValue.KAKAO_WEBTOON_WRITER).first()).child(2).text().split(",");

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
            case WebtoonCrawlingValue.WEBTOON_RANKING_1 -> score = WebtoonCrawlingValue.RANKING_1_SCORE;
            case WebtoonCrawlingValue.WEBTOON_RANKING_2 -> score = WebtoonCrawlingValue.RANKING_2_SCORE;
            case WebtoonCrawlingValue.WEBTOON_RANKING_3 -> score = WebtoonCrawlingValue.RANKING_3_SCORE;
            case WebtoonCrawlingValue.WEBTOON_RANKIG_4 -> score = WebtoonCrawlingValue.RANKING_4_SCORE;
            case WebtoonCrawlingValue.WEBTOON_RANKING_5 -> score = WebtoonCrawlingValue.RANKING_5_SCORE;
            default -> score = WebtoonCrawlingValue.DEFAULT_SCORE;
        }
        return score;
    }
}
