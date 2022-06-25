package kr.co.antoon.vote.domain;

import kr.co.antoon.vote.domain.vo.VoteResult;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Candidate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    private String imageUrl;

    private Long votingCount;

    private double votingCountRate;

    private Long topicId;

    @Enumerated(EnumType.STRING)
    private VoteResult voteResult;

    public void plusVotingCount() {
        this.votingCount += 1;
    }

    public void updateVotingRate(double votingRate) {
        this.votingCountRate = votingRate;
    }

    public void updateVoteResult(VoteResult voteResult) {
        this.voteResult = voteResult;
    }
}
