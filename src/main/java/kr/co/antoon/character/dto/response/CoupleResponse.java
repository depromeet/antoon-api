package kr.co.antoon.character.dto.response;

import kr.co.antoon.character.domain.Character;

import java.util.List;

public record CoupleResponse(
        List<CoupleDetailResponse> couples
) {
    public record CoupleDetailResponse(
            String name1,
            String name2,
            String title,
            String imageUrl1,
            String imageUrl2,
            Long amount,
            Boolean isJoined
    ) {
        public CoupleDetailResponse(Character couple, Boolean isJoined) {
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
