package kr.co.antoon.feedback.domain.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum Score {
    ONE_STAR(1),
    TWO_STAR(2),
    THREE_STAR(3),
    FOUR_STAR(4),
    FIVE_STAR(5),
    ;

    private final int weight;

    public static Score of(int weight) {
        return Arrays.stream(Score.values())
                .filter(c -> c.getWeight() == weight)
                .findAny()
                .orElse(THREE_STAR);
    }
}
