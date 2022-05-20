package kr.co.antoon.webtoon.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.co.antoon.webtoon.domain.Webtoon;

import java.util.List;

@Schema(description = "공통 Response")
public record WebtoonResponse(
        @Schema(description = "웹툰 id")
        Long id,
        @Schema(description = "제목")
        String title,
        @Schema(description = "소개")
        String content,
        @Schema(description = "작가")
        List<String> writers,
        @Schema(description = "웹툰 url")
        String url,
        @Schema(description = "썸네일")
        String thumbnail,
        @Schema(description = "웹툰 상태")
        String activeStatus,
        @Schema(description = "웹툰 플랫폼")
        String platform,
        @Schema(description = "웹툰 정르")
        List<String> genres,
        @Schema(description = "웹툰 연재요일")
        List<String> days
) {
    public WebtoonResponse(
            Webtoon webtoon,
            List<String> writers,
            List<String> genres,
            List<String> days
    ) {
        this(
                webtoon.getId(),
                webtoon.getTitle(),
                webtoon.getContent(),
                writers,
                webtoon.getWebtoonUrl(),
                webtoon.getThumbnail(),
                webtoon.getStatus().getDescription(),
                webtoon.getPlatform().getDescription(),
                genres,
                days
        );
    }
}