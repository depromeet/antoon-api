package kr.co.antoon.crawling.dto;

import java.util.List;
import java.util.Set;

public record WebtoonCrawlingDto(
        Set<WebtoonCrawlingDetail> webtoons
) {
    public record WebtoonCrawlingDetail(
            String title,
            String content,
            List<String> writer,
            String url,
            String thumbnail,
            List<String> genre,
            Double score,
            String day
    ) { }
}
