package kr.co.antoon.webtoon.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kr.co.antoon.webtoon.domain.Webtoon;
import kr.co.antoon.webtoon.domain.vo.ActiveStatus;
import kr.co.antoon.webtoon.domain.vo.Platform;

import java.util.List;

@ApiModel("웹툰 공통 Response")
public record WebtoonResponse(
        @ApiModelProperty(
                position = 1,
                example = "1",
                value = "webtoon id"
        )
        Long id,

        @ApiModelProperty(
                position = 2,
                example = "약한영웅",
                value = "webtoon title"
        )
        String title,

        @ApiModelProperty(
                position = 3,
                example = "약한영웅의 반칙",
                value = "webtoon content"
        )
        String content,

        @ApiModelProperty(
                position = 4,
                example = "['나나은', '김동건']",
                value = "작가"
        )
        List<String> writers,

        @ApiModelProperty(
                position = 5,
                example = "https://comic.naver.com/webtoon/list?titleId=794154&no=4&weekday=",
                value = "웹툰 url"
        )
        String url,

        @ApiModelProperty(
                position = 6,
                example = "https://shared-comic.pstatic.net/thumb/webtoon/794154/thumbnail/thumbnail_IMAG06_889def63-5f8e-4aa8-b007-97a3d86aca68.jpg",
                value = "웹툰 썸네일"
        )
        String thumbnail,

        @ApiModelProperty(
                position = 7,
                example = "https://shared-comic.pstatic.net/thumb/webtoon/794154/thumbnail/thumbnail_IMAG06_889def63-5f8e-4aa8-b007-97a3d86aca68.jpg",
                value = "웹툰 썸네일"
        )
        ActiveStatus activeStatus,

        @ApiModelProperty(
                position = 8,
                example = "NAVER",
                value = "웹툰 플랫폼"
        )
        Platform platform,

        @ApiModelProperty(
                position = 9,
                example = "EVERYDAY",
                value = "장르"
        )
        List<String> genres,

        @ApiModelProperty(
                position = 10,
                example = "['월', '수']",
                value = "연재 요일"
        )
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
                webtoon.getStatus(),
                webtoon.getPlatform(),
                genres,
                days
        );
    }
}
