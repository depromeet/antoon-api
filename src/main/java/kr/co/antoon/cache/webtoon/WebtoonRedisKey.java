package kr.co.antoon.cache.webtoon;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum WebtoonRedisKey {
    WEBTOON_TOP_RANK_KEY("webtoon::top-rank"),
    WEBTOON_SEARCH_KEY("webtoon::search"),
    WEBTOON_TOP_UPPER("webtoon::top::upper"),
    ;

    private final String value;
}
