package kr.co.antoon.cache.webtoon;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum WebtoonRedisKey {
    WEBTOON_TOP_RANK_KEY("webtoon::top-rank"),
    ;

    private final String value;
}
