package kr.co.antoon.webtoon.crawling;

import kr.co.antoon.error.dto.ErrorMessage;
import kr.co.antoon.error.exception.common.NotExistsException;
import kr.co.antoon.webtoon.domain.vo.Platform;

public class WebtoonCrawlingFactory {

    public static WebtoonCrawling of(Platform platform) {
        return switch (platform) {
            case NAVER -> new NaverWebtoonCrawling();
            case KAKAO -> new KakaoWebtoonCrawling();
            default -> throw new NotExistsException(ErrorMessage.NOT_EXISTS_WEBTOON_PLATFORM_TYPE_ERROR);
        };
    }
}