package kr.co.antoon.vote.domain.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum VoteCategory {
    AB("A/B"),
    MULTIPLE("Multiple");

    private final String description;
}
