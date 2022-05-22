package kr.co.antoon.crawling;

import kr.co.antoon.webtoon.domain.vo.Platform;

public class WebtoonCrawlingFactory {
    public static WebtoonCrawling of(Platform platform) {
        return switch (platform) {
            case NAVER -> new NaverWebtoonCrawling();
            case KAKAO -> new KakaoWebtoonCrawling();
        };
    }
}