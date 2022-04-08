package kr.co.toonzip.webtoon.dto;

import lombok.Getter;

import java.util.List;

public record WebtoonCrawlingDto(
        List<WebtoonCrawlingDetail> dto
) {
    public record WebtoonCrawlingDetail(
            String title,
            String content,
            String writer,
            String url,
            String thumbnail,
            String genre,
            Double score,
            String day
    ) { }
}