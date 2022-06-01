package kr.co.antoon.user.domain.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Status {
    SUCCESS("로그인 성공"),
    SIGNUP("회원가입"),
    ;

    private final String description;


}
