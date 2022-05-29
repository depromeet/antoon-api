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
        switch (gender) {
            case "female":
                return Gender.FEMALE;
            case "male":
                return Gender.MALE;
            default:
                return Gender.NONE;
        }
    }
}