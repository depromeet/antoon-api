package kr.co.antoon.graph.domain.vo;

import kr.co.antoon.error.exception.graph.NotExistsGraphPeriodTypeException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum Period {
    DAY("day", 1),
    WEEKEND("weekend", 7),
    MONTH("month", 30),
    THREE_MONTH("three-month", 90),
    ;

    private final String description;
    private final int days;

    public static Period of(String description) {
        return Arrays.stream(Period.values())
                .filter(c -> c.getDescription().equals(description))
                .findAny()
                .orElseThrow(NotExistsGraphPeriodTypeException::new);
    }
}
