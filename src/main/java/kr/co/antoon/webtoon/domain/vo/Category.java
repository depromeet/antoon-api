package kr.co.antoon.webtoon.domain.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum Category {

    THRILLER("스릴러"),
    OMNIBUS("옴니버스"),
    STORY("스토리"),
    ACTION("액션"),
    EPISODE("에피소드"),
    GAG("개그"),
    EVERYDAY("일상"),
    DRAMA("드라마"),
    ROMANCE("로맨스"),
    FANTASE("판타지"),
    HISTORY("무협/사극"),
    SPORT("스포츠"),
    EMOTION("감성"),
    NONE("없음");

    private final String description;

    public static Category of(String description) {
        System.out.println("origin > " + description);
        Category result = Arrays.stream(Category.values())
                .filter(c -> c.getDescription().equals(description))
                .findAny()
                .orElse(NONE);
        System.out.println("result > " + result);
        return result;
    }
}
