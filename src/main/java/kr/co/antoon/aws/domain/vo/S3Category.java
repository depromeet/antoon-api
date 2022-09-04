package kr.co.antoon.aws.domain.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum S3Category {
    PROFILE("사용자 프로필 이미지"),
    WEBTOON_CHARACTER("웹툰 캐릭터"),
    VOTE("투표"),
    TEST("업로드 테스트용"),
    ANT_DEFAULT("기본 개미 이미지"),
    ADULT_THUMBNAIL("성인용 웹툰 썸네일")
    ;
    
    private final String description;
}
