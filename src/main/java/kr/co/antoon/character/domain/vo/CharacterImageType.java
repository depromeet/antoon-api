package kr.co.antoon.character.domain.vo;

import kr.co.antoon.error.dto.ErrorMessage;
import kr.co.antoon.error.exception.common.NotExistsException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CharacterImageType {
    PERSONA("인물 차트"),
    COUPLE("커플 차트"),
    PERSONADETAIL("인물 상세"),
    COUPLEDETAIL("커플 상세"),
    ;

    private final String description;

    public static CharacterImageType of(String type) {
        return switch (CharacterImageType.valueOf(type)) {
            case PERSONA -> PERSONADETAIL;
            case COUPLE -> COUPLEDETAIL;
            default -> throw new NotExistsException(ErrorMessage.NOT_EXISTS_CHARACTER_IMAGE);
        };
    }

    public static CharacterImageType type(String type) {
        return switch (CharacterImageType.valueOf(type)) {
            case PERSONA -> PERSONADETAIL;
            default -> COUPLE;
        };
    }
}
