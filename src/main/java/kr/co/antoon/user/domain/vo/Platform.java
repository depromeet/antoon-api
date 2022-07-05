package kr.co.antoon.user.domain.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

// TODO : user table에 어떤 플랫폼에서 가입했는지 추가하는건?
@Getter
@RequiredArgsConstructor
public enum Platform {
    KAKAO("kakao"),
    GOOGLE("google"),
    NONE("none"),
    ;

    private final String description;

    public static Platform of(String description) {
        return Arrays.stream(Platform.values())
                .filter(d -> d.description.equals(description))
                .findAny()
                .orElse(NONE);
    }
}
