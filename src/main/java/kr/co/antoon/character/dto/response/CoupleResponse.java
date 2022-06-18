package kr.co.antoon.character.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.co.antoon.character.domain.Character;

import java.util.List;

public record CoupleResponse(
        List<CoupleDetailResponse> couples
) {
    public record CoupleDetailResponse(
            @Schema(description = "인물 이름")
            List<String> names,
            @Schema(description = "웹툰명")
            String title,
            @Schema(description = "인물 사진")
            List<String> thumbnails,
            @Schema(description = "코인")
            Long coinAmount,
            @Schema(description = "탑승 여부")
            Boolean isJoined
    ) {
        public CoupleDetailResponse(Character couple, String title, Boolean isJoined) {
            this(
                    List.of(couple.getName().split(",")),
                    title,
                    List.of(couple.getThumbnail().split(",")),
                    couple.getCoinAmount(),
                    isJoined
            );
        }
    }
}
