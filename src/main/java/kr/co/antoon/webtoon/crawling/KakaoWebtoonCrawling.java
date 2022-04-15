package kr.co.antoon.webtoon.crawling;

import kr.co.antoon.webtoon.domain.vo.Platform;
import kr.co.antoon.webtoon.dto.WebtoonCrawlingDto;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component("kakaoWebtoon")
public class KakaoWebtoonCrawling implements WebtoonCrawling {

    @Override
    public WebtoonCrawlingDto crawling() {

        var bundle = new ArrayList<WebtoonCrawlingDto.WebtoonCrawlingDetail>();

        try {
            var kakaoWebtoonDocument = Jsoup.connect("https://page.kakao.com/main?categoryUid=10&subCategoryUid=10000").get();
            var webtoonContentsElements = kakaoWebtoonDocument.select("div.css-19y0ur2");

            for (Element contentElement : webtoonContentsElements) {
                var aLinkElements = contentElement.select("a");

                for (Element aLinkElement : aLinkElements) {
                    var url = "https://page.kakao.com" + aLinkElement.attr("href");
                    var webtoonDetailDocument = Jsoup.connect(url).get();

                    var innerElements = webtoonDetailDocument.select("div.css-1ydjg2i");
                    innerElements.forEach(innerElement -> {
                        var title = innerElement.select("h2.text-ellipsis.css-jgjrt").text();
                        var dayInfoBox = innerElement.select("div.css-ymlwac").first().child(1).text().split("\\|");
                        var day = dayInfoBox[0];
                        var writer = innerElement.select("div.css-ymlwac").first().child(2).text();

                        // TODO: 구현이 남은 부분
                        var content = "";
                        var thumbnail = "";
                        List<String> genres = new ArrayList<>();
                        var score = "0.0";

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
}
