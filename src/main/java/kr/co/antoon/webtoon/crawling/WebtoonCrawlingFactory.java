package kr.co.antoon.webtoon.crawling;

import kr.co.antoon.error.dto.ErrorMessage;
import kr.co.antoon.error.exception.common.NotExistsException;
import kr.co.antoon.webtoon.domain.vo.Platform;
import org.springframework.http.HttpStatus;

public class WebtoonCrawlingFactory {

    public static WebtoonCrawling of(Platform platform) {
        return switch (platform) {
            case NAVER -> new NaverWebtoonCrawling();
            case KAKAO -> new NaverWebtoonCrawling(); // TODO KAKAO 웹툰으로 바꿔주세요!!
            default -> throw new NotExistsException(ErrorMessage.NOT_EXISTS_WEBTOON_TYPE_ERROR, HttpStatus.NOT_FOUND);
        };
    }
}