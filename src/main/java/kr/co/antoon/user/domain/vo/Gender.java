package kr.co.antoon.user.domain.vo;

import kr.co.antoon.error.dto.ErrorMessage;
import kr.co.antoon.error.exception.oauth.NotValidRoleException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

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