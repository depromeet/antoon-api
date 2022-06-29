package kr.co.antoon.character.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.co.antoon.character.domain.Character;
import kr.co.antoon.webtoon.dto.query.WebtoonCharacterNativeDto;

public record CharacterDetailResponse(
        @Schema(description = "인물/커플 ID")
        Long id,
        @Schema(description = "인물/커플 이름")
        String name,
        @Schema(description = "인물/커플 이미지")
        String characterThumbnail,
        @Schema(description = "인물/커플 순위")
        Integer rank,
        @Schema(description = "상세페이지 배경색")
        String backGroundColor,
        @Schema(description = "인물/커플 누적 안트코인")
        Long coinAmount,
        @Schema(description = "웹툰 ID")
        Long webtoonId,
        @Schema(description = "웹툰 썸네일")
        String webtoonThumbnail,
        @Schema(description = "웹툰 제목")
        String title,
        @Schema(description = "웹툰 점수")
        Integer score,
        @Schema(description = "인물/커플 탐승 여부")
        Boolean isJoined,
        @Schema(description = "인물/커플 탑승 수")
        Integer joinedCount
)
{
        public CharacterDetailResponse(Character character, Integer rank, String imageUrl, WebtoonCharacterNativeDto webtoon, Boolean isJoined, Integer joinedCount) {
                this(
                        character.getId(),
                        character.getName(),
                        imageUrl,
                        rank,
                        character.getColor(),
                        character.getCoinAmount(),
                        webtoon.getWebtoonId(),
                        webtoon.getThumbnail(),
                        webtoon.getTitle(),
                        webtoon.getScore(),
                        isJoined,
                        joinedCount
                );
        }
}
