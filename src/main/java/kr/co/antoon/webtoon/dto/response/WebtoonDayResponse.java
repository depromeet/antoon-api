package kr.co.antoon.webtoon.dto.response;

import java.util.List;

public record WebtoonDayResponse(
        String thumbnail,
        String title,
        List<String> writers,
        String day
){ }