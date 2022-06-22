package kr.co.antoon.webtoon.dto.response;

import kr.co.antoon.webtoon.dto.WebtoonDto;

import java.util.List;

public record WebtoonSearchResponse(
        List<WebtoonDto> webtoons
) { }
