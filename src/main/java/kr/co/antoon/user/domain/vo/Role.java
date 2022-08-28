package kr.co.antoon.user.domain.vo;

import kr.co.antoon.error.exception.user.InvalidUserRoleException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum Role {
    USER("ROLE_USER", "일반 사용자"),
    ADMIN("ROLE_ADMIN", "관리자"),
    ;

    private final String key;
    private final String title;

    public static Role of(String key) {
        return Arrays.stream(Role.values())
                .filter(role -> role.getKey().equals(key))
                .findAny()
                .orElseThrow(InvalidUserRoleException::new);
    }
}
