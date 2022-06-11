package kr.co.antoon.character.dto.response;

import kr.co.antoon.character.domain.Character;

import java.util.List;

public record CharacterResponse(
        List<CharacterDetailResponse> characters
) {
    public record CharacterDetailResponse(
            String name,
            String title,
            String imageUrl,
            Long amount,
            Boolean isJoined
    ) {
        public CharacterDetailResponse(Character character, Boolean isJoined) {
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
