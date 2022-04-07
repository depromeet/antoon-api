package kr.co.toonzip.webtoon.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class WebtoonCrawlingDto {
    private final List<WebtoonCrawlingDetail> dto;

    @Getter
    @Builder
    public static class WebtoonCrawlingDetail {
        private final String title;
        private final String content;
        private final String writer;
        private final String url;
        private final String thumbnail;
        private final String genre;
        private final Double score;
        private final String day;
    }
}

