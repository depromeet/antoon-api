package kr.co.antoon.vote.domain.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum VoteType {
    A_TYPE("A"),
    B_TYPE("B");

    private final String description;
}
