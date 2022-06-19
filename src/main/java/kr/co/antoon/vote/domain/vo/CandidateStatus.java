package kr.co.antoon.vote.domain.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CandidateStatus {
    WINNER("winner"),
    LOSER("loser"),
    DRAW("draw");

    private final String description;
}
