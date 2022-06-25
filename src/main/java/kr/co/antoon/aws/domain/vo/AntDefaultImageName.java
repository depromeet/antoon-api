package kr.co.antoon.aws.domain.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Random;

@Getter
@RequiredArgsConstructor
public enum AntDefaultImageName {
    blue("파랑 개미"),
    green("초록 개미"),
    marine("하늘 개미"),
    mint("마린 개미"),
    orange("주황 개미"),
    purple("보라 개미"),
    red("빨강 개미"),
    yellow("노랑 개미"),
    ;
    private final String description;

    public static AntDefaultImageName getRandomAnt() {
        Random random = new Random();
        return values()[random.nextInt(values().length)];
    }
}
