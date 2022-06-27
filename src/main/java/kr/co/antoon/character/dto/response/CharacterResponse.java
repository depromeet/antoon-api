package kr.co.antoon.character.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.co.antoon.character.domain.Character;

import java.util.List;

public record CharacterResponse(
        List<CharacterDetailResponse> couples
) {
    public record CharacterDetailResponse(
            @Schema(description = "인물/커플 ID")
            Long id,
            @Schema(description = "인물/커플 이름")
            String names,
            @Schema(description = "웹툰명")
            String title,
            @Schema(description = "인물/커플 사진")
            List<String> thumbnails,
            @Schema(description = "인물/커플 누적 코인")
            Long coinAmount,
            @Schema(description = "탑승 여부")
            Boolean isJoined
    ) {
        public CharacterDetailResponse(Character character, String imageUrl, String title, Boolean isJoined) {
            this(
                    character.getId(),
                    character.getName(),
                    title,
                    List.of(imageUrl.split(",")),
                    character.getCoinAmount(),
                    isJoined
            );
        }
    }
}
