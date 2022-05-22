package kr.co.antoon.webtoon.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel("웹툰 요일별 리스트")
public record WebtoonDayResponse(
        @ApiModelProperty(
                position = 1,
                example = "https://shared-comic.pstatic.net/thumb/webtoon/794154/thumbnail/thumbnail_IMAG06_889def63-5f8e-4aa8-b007-97a3d86aca68.jpg",
                value = "웹툰 썸네일"
        )
        String thumbnail,

        @ApiModelProperty(
                position = 2,
                example = "남편을 먹는 여자",
                value = "웹툰 제목"
        )
        String title,

        @ApiModelProperty(
                position = 3,
                example = "['나나은', '김동건']",
                value = "작가"
        )
        List<String> writers,

        @ApiModelProperty(
                position = 4,
                example = "수",
                value = "연재요일"
        )
        String day
) { }