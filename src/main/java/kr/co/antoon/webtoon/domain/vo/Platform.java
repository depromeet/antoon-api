package kr.co.antoon.webtoon.domain.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Platform {
    NAVER("네이버 웹툰"),
    KAKAO("카카오 웹툰"),
    LEZHIN("레진 코믹스");

    private final String name;
}