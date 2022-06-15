package kr.co.antoon.coin.domain.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RemittanceStatus {
    PLUS("추가"),
    MINUS("감소"),
    ;

    private final String description;
}
