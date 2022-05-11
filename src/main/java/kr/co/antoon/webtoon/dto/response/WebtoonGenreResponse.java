package kr.co.antoon.webtoon.dto.response;

import kr.co.antoon.webtoon.domain.vo.Platform;

import java.util.List;

public record WebtoonGenreResponse(
        String thumbnail,
        String title,
        int graphScore,
        double scoreGap,
        List<String> writers,
        Platform platform,
        String genre
){ }