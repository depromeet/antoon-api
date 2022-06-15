package kr.co.antoon.subject.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.co.antoon.subject.domain.Subject;

import java.util.List;

public record CharacterResponse(
        List<CharacterDetailResponse> characters
) {
    public record CharacterDetailResponse(
            @Schema(description = "인물 이름")
            String name,
            @Schema(description = "웹툰명")
            String title,
            @Schema(description = "인물 사진")
            String imageUrl,
            @Schema(description = "코인")
            Long amount,
            @Schema(description = "탑승 여부")
            Boolean isJoined
    ) {
        public CharacterDetailResponse(Subject character, Boolean isJoined) {
            this(
                    character.getName(),
                    character.getTitle(),
                    character.getImageUrl(),
                    character.getAmount(),
                    isJoined
            );
        }
    }
}
