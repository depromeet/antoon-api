package kr.co.antoon.subject.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.co.antoon.subject.domain.Subject;

import java.util.List;

public record CoupleResponse(
        List<CoupleDetailResponse> couples
) {
    public record CoupleDetailResponse(
            @Schema(description = "인물 이름1")
            String name1,
            @Schema(description = "인물 이름2")
            String name2,
            @Schema(description = "웹툰명")
            String title,
            @Schema(description = "인물 사진1")
            String imageUrl1,
            @Schema(description = "인물 사진2")
            String imageUrl2,
            @Schema(description = "코인")
            Long amount,
            @Schema(description = "탑승 여부")
            Boolean isJoined
    ) {
        public CoupleDetailResponse(Subject couple, Boolean isJoined) {
            this(
                    couple.getName().split(",")[0],
                    couple.getName().split(",")[1],
                    couple.getTitle(),
                    couple.getImageUrl().split(",")[0],
                    couple.getImageUrl().split(",")[1],
                    couple.getAmount(),
                    isJoined
            );
        }
    }
}
