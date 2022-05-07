package kr.co.antoon.webtoon.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.co.antoon.webtoon.domain.Webtoon;
import lombok.Getter;

import java.util.List;

@Getter
@Schema(description = "공통 Response")
public class WebtoonResponse {
    @Schema(description = "웹툰 id")
    private final Long id;
    @Schema(description = "제목")
    private final String title;
    @Schema(description = "소개")
    private final String content;
    @Schema(description = "작가")
    private final List<String> writers;
    @Schema(description = "웹툰 url")
    private final String url;
    @Schema(description = "썸네일")
    private final String thumbnail;
    @Schema(description = "웹툰 상태")
    private final String activeStatus;
    @Schema(description = "웹툰 플랫폼")
    private final String platform;
    @Schema(description = "웹툰 정르")
    private final List<String> genres;
    @Schema(description = "웹툰 연재요일")
    private final List<String> days;

    public WebtoonResponse(
            Webtoon webtoon,
            List<String> writers,
            List<String> genres,
            List<String> days
    ) {
        this.id = webtoon.getId();
        this.title = webtoon.getTitle();
        this.content = webtoon.getContent();
        this.writers = writers;
        this.url = webtoon.getUrl();
        this.thumbnail = webtoon.getThumbnail();
        this.activeStatus = webtoon.getStatus().getDescription();
        this.platform = webtoon.getPlatform().getDescription();
        this.genres = genres;
        this.days = days;
    }
}