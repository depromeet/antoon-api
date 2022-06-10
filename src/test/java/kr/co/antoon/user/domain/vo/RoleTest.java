package kr.co.antoon.user.domain.vo;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RoleTest {
    @ParameterizedTest
    @CsvSource({
            "ROLE_USER, USER",
            "ROLE_ADMIN, ADMIN"
    })
    void 권한_정보_처리(String role, Role expected) {
        Role actual = Role.of(role);

        assertEquals(expected, actual);
    }
}