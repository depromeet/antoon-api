package kr.co.antoon.vote.domain;

import kr.co.antoon.common.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Candidate extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    private String imageUrl;

    private Long votingCount;

    private double votingCountRate;

    private Long topicId;

    private Boolean winner = false;

    public void plusVotingCount() {
        this.votingCount += 1;
    }

    public void updateVotingRate(double votingRate) {
        this.votingCountRate = votingRate;
    }

    public void updateWinner(Boolean winner) {
        this.winner = winner;
    }
}
