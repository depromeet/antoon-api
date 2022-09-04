package kr.co.antoon.aws.domain.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AdultThumbnailType {
    KAKAO("카카오 웹툰"),
    NAVER("네이버 웹툰"),
    DETAIL("상세 조회"),
    RANK("랭킹 조회"),
    ;

    private final String description;
}
