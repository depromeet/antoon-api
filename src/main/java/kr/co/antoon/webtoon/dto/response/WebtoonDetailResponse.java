package kr.co.antoon.webtoon.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.co.antoon.webtoon.domain.vo.ActiveStatus;
import kr.co.antoon.webtoon.domain.vo.GenreCategory;
import kr.co.antoon.webtoon.domain.vo.Platform;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
@Builder
@Schema(description = "웹툰 상세 설명 DTO")
public class WebtoonDetailResponse {
    @Schema(description = "제목")
    private final String title;
    @Schema(description = "소개")
    private final String content;
    @Schema(description = "작가")
    private final List<String> writer;
    @Schema(description = "웹툰 url")
    private final String url;
    @Schema(description = "썸네일")
    private final String thumbnail;
    @Schema(description = "장르")
    private final List<GenreCategory> genre;
    @Schema(description = "완결 여부")
    private final ActiveStatus status;
    @Schema(description = "플랫폼")
    private final Platform platform;
}