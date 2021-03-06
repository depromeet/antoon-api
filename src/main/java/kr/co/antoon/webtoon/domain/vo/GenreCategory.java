package kr.co.antoon.webtoon.domain.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum GenreCategory {
    EVERYDAY("일상"),
    GAG("개그"),
    FANTASE("판타지"),
    ACTION("액션"),
    DRAMA("드라마"),
    ROMANCE("로맨스"),
    EMOTION("감성"),
    THRILLER("스릴러"),
    HISTORY("무협/사극"),
    SPORT("스포츠"),
    OMNIBUS("옴니버스"),
    STORY("스토리"),
    EPISODE("에피소드"),
    NONE("없음"),
    ;

    private final String description;

    public static GenreCategory of(String description) {
        return Arrays.stream(GenreCategory.values())
                .filter(c -> c.getDescription().equals(description))
                .findAny()
                .orElse(NONE);
    }
}
