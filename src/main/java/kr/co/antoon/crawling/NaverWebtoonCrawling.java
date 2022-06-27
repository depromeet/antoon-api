package kr.co.antoon.crawling;

import kr.co.antoon.crawling.dto.WebtoonCrawlingDto;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class NaverWebtoonCrawling implements WebtoonCrawling {
    @Override
    public WebtoonCrawlingDto crawling() {
        var bundle = new HashSet<WebtoonCrawlingDto.WebtoonCrawlingDetail>();

        try {
            var naverWeekendWebtoonDocument = Jsoup.connect("https://comic.naver.com/webtoon/weekday").get();
            var webtoonElements = naverWeekendWebtoonDocument.select("div.list_area.daily_all div.col");

            for (var weekendWebtoons : webtoonElements) {
                var eachUrlElements = weekendWebtoons.select("ul li div.thumb a");

                for (Element webtoon : eachUrlElements) {
                    var url = "https://comic.naver.com" + webtoon.attr("href");
                    var day = weekendWebtoons.getElementsByTag("h4").text().substring(0, 1);

                    var webtoonDetailDocument = Jsoup.connect(url).get();

                    var score = webtoonDetailDocument.select("div.rating_type")
                            .get(0)
                            .getElementsByTag("strong")
                            .text();

                    var detailUrl = "https://comic.naver.com" + webtoonDetailDocument.select("td.title a").get(0).attr("href");

                    var webtoonDetailImageDocument = Jsoup.connect(detailUrl).get();

                    var thumbnail = webtoonDetailImageDocument.select("div.thumb img").attr("src");

                    // TODO : 19금 이미지인 경우 교체 사진 필요
                    if (!StringUtils.hasText(thumbnail)) {
                        thumbnail = "https://www.easylaw.go.kr/CSP/template/2019/08/22/BIN000D[31].bmp";
                    }

                    var innerElements = webtoonDetailDocument.select("div.comicinfo");

                    String finalThumbnail = thumbnail;
                    bundle.addAll(innerElements
                            .parallelStream()
                            .map(innerElement -> {
                                var title = innerElement.select("span.title").text();
                                var content = innerElement.getElementsByTag("p").get(0).text();
                                var writer = innerElement.select("span.wrt_nm").text().split(" / ");
                                var genre = innerElement.select("span.genre").text().split(",");

                                var genres = Arrays.stream(genre)
                                        .map(g -> g.replace(" ", ""))
                                        .collect(Collectors.toList());

                                //  log.info("[Naver Webtoon Crawling] title-> {} / url -> {}", title, url);

                                return new WebtoonCrawlingDto.WebtoonCrawlingDetail(
                                        title,
                                        content,
                                        List.of(writer),
                                        url,
                                        finalThumbnail,
                                        genres,
                                        Double.parseDouble(score),
                                        day
                                );
                            }).toList());
                }
            }
        } catch (IOException e) {
            log.error("[Naver Webtoon Crawling Error] {}", e.getCause().toString());
        }

        return new WebtoonCrawlingDto(bundle);
    }
}
