package kr.co.antoon.webtoon.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kr.co.antoon.webtoon.domain.vo.GenreCategory;
import kr.co.antoon.webtoon.domain.vo.Platform;
import kr.co.antoon.webtoon.dto.query.WebtoonGenreNativeDto;

import java.util.List;

import static kr.co.antoon.criteria.BasicAllocateScore.getDifferencePercentage;

@ApiModel("장르별 활성화된 웹툰 조회 API")
public record WebtoonGenreResponse(
        @ApiModelProperty(
                position = 1,
                example = "1",
                value = "웹툰 id"
        )
        Long webtoonId,

        @ApiModelProperty(
                position = 2,
                example = "https://shared-comic.pstatic.net/thumb/webtoon/794154/thumbnail/thumbnail_IMAG06_889def63-5f8e-4aa8-b007-97a3d86aca68.jpg",
                value = "웹툰 썸네일"
        )
        String thumbnail,

        @ApiModelProperty(
                position = 3,
                example = "남편을 먹는 여자",
                value = "웹툰 제목"
        )
        String title,

        @ApiModelProperty(
                position = 4,
                example = "650",
                value = "그래프 점수"
        )
        int graphScore,

        @ApiModelProperty(
                position = 5,
                example = "35",
                value = "스코어 갭"
        )
        double scoreGap,

        @ApiModelProperty(
                position = 6,
                example = "['나나은', '김동건']",
                value = "작가"
        )
        List<String> writers,

        @ApiModelProperty(
                position = 7,
                example = "NAVER",
                value = "웹툰 플랫폼"
        )
        Platform platform,

        @ApiModelProperty(
                position = 8,
                example = "EVERYDAY",
                value = "장르"
        )
        String genre
) {
    public WebtoonGenreResponse(
            WebtoonGenreNativeDto webtoon,
            List<String> writers,
            GenreCategory genreCategory
    ) {
        this(
                webtoon.getWebtoonId(),
                webtoon.getThumbnail(),
                webtoon.getTitle(),
                webtoon.getGraphScore(),
                getDifferencePercentage(webtoon.getGraphScore(), webtoon.getScoreGap()),
                writers,
                webtoon.getPlatform(),
                genreCategory.getDescription()
        );
    }
}