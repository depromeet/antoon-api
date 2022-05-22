package kr.co.antoon.crawling.dto;

import java.util.List;

public record WebtoonCrawlingDto(
        List<WebtoonCrawlingDetail> webtoons
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