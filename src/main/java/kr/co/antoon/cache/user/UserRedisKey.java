package kr.co.antoon.cache.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserRedisKey {
    USER_KEY("refresh:user_"),
    ;

    private final String value;
}
