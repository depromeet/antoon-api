package kr.co.antoon.user.domain.vo;

import kr.co.antoon.oauth.application.CustomOAuth2UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Status {

    SUCCESS("로그인 성공"),
    SIGNUP("회원가입"),
    ;

    private final String description;

    public static Status of(Boolean check) {
        if (!check) {
            return SIGNUP;
        }
        return SUCCESS;
    }
}
