package kr.co.antoon.webtoon.dto;

import kr.co.antoon.webtoon.domain.vo.ActiveStatus;
import kr.co.antoon.webtoon.domain.vo.Category;
import kr.co.antoon.webtoon.domain.vo.Platform;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@RequiredArgsConstructor
@Builder
//swagger 적용하기
public class WebtoonDetailDto {
    private final String title; //제목
    private final String content; //소개
    private final List<String> writer; //작가
    private final String url; //웹툰 url
    private final String thumbnail; //썸네일
    private final List<Category> genre; //장르
    private final ActiveStatus status; //완결 여부
    private final Platform platform; // 플랫폼
}
