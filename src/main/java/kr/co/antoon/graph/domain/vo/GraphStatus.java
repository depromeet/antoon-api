package kr.co.antoon.graph.domain.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum GraphStatus {
    UP("상승"),
    DOWN("하락"),
    MAINTATIN("유지"),
    ;

    private final String description;

    public static GraphStatus of(double oldScore, double newScore) {
        double data = newScore - oldScore;

        if (data > 0) {
            return UP;
        } else if (data < 0) {
            return DOWN;
        } else {
            return MAINTATIN;
        }
    }
}