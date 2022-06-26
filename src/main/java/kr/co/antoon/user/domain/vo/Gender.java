package kr.co.antoon.user.domain.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Gender {
    MALE("남성"),
    FEMALE("여성"),
    NONE("없음"),
    ;

    private final String description;

    public static Gender of(String gender) {
        return switch (gender) {
            case "female" -> Gender.FEMALE;
            case "male" -> Gender.MALE;
            default -> Gender.NONE;
        };
    }
}
