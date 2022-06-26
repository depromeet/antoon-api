package kr.co.antoon.webtoon.dto.response;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kr.co.antoon.webtoon.domain.Webtoon;
import kr.co.antoon.webtoon.domain.WebtoonGenre;
import kr.co.antoon.webtoon.domain.vo.GenreCategory;
import kr.co.antoon.webtoon.dto.query.WebtoonGenreBannerNativeDto;

import java.util.List;

@ApiModel("웹툰 장르별 리스트")
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "@class")
public record WebtoonGenreAllResponse(
        List<WebtoonGenrePreviewResponse> webtoons
) {
    public record WebtoonGenrePreviewResponse(
            @ApiModelProperty(
                    position = 1,
                    example = "EVERYDAY",
                    value = "장르"
            )
            String genre,

            @ApiModelProperty(
                    position = 2,
                    example = "https://shared-comic.pstatic.net/thumb/webtoon/794154/thumbnail/thumbnail_IMAG06_889def63-5f8e-4aa8-b007-97a3d86aca68.jpg",
                    value = "웹툰 썸네일"
            )
            String thumbnail
    ) {
        public WebtoonGenrePreviewResponse(
                WebtoonGenreBannerNativeDto dto
        ) {
            this(dto.getGenreCategory().getDescription(), dto.getThumbnail());
        }

        public WebtoonGenrePreviewResponse(
                GenreCategory genreCategory, String thumbnail
        ) {
            this(genreCategory.name(), thumbnail);
        }

        public WebtoonGenrePreviewResponse(WebtoonGenre webtoonGenre, Webtoon webtoon) {
            this(
                    webtoonGenre.getGenreCategory().getDescription(),
                    webtoon.getThumbnail()
            );
        }
    }
}
