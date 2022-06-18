package kr.co.antoon.character.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.co.antoon.character.domain.Character;

import java.util.List;

public record PersonaResponse(
        List<CharacterDetailResponse> characters
) {
    // TODO: 랭크 변동값 선택
    public record CharacterDetailResponse(
            @Schema(description = "인물 이름")
            String name,
            @Schema(description = "웹툰명")
            String title,
            @Schema(description = "인물 사진")
            String thumbnail,
            @Schema(description = "코인")
            Long coinAmount,
            @Schema(description = "탑승 여부")
            Boolean isJoined
    ) {
        public CharacterDetailResponse(Character character, String title, Boolean isJoined) {
            this(
                    character.getName(),
                    title,
                    character.getThumbnail(),
                    character.getCoinAmount(),
                    isJoined
            );
        }
    }
}
