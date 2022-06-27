package kr.co.antoon.graph.domain.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum GraphStatus {
    UP("상승"),
    DOWN("하락"),
    MAINTAIN("유지"),
    ;

    private final String description;

    public static GraphStatus of(double data) {
        if (data > 0) {
            return UP;
        }

        if (data < 0) {
            return DOWN;
        }

        return MAINTAIN;
    }
}
