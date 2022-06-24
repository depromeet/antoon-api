package kr.co.antoon.webtoon.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kr.co.antoon.webtoon.domain.Webtoon;

import java.util.List;

@ApiModel("웹툰 전체 리스트")
public record WebtoonAllResponse(
        List<WebtoonDetail> webtoons
) {
    public record WebtoonDetail(
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
            String title
    ) {
        public WebtoonDetail(Webtoon webtoon) {
            this(webtoon.getId(), webtoon.getTitle());
        }
    }
}
