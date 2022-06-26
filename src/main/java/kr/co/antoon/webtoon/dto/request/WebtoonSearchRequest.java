package kr.co.antoon.webtoon.dto.request;

import java.util.List;

public record WebtoonSearchRequest(
        List<Long> webtoons
) { }
