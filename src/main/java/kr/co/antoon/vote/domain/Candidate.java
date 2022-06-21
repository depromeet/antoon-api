package kr.co.antoon.vote.domain;

import kr.co.antoon.vote.domain.vo.CandidateStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Candidate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    private String imageUrl;

    private Long votingCount;   // 득표율

    private double votingCountRate;    // 득표율 퍼센트

    private Long topicId;

    @Enumerated(EnumType.STRING)
    private CandidateStatus candidateStatus;

    public void update(CandidateStatus candidateStatus) {
        this.votingCount += 1;
        this.votingCountRate += 1;
        this.candidateStatus = candidateStatus;
    }

    public void plusVotingCount() {
        this.votingCount += 1;
    }

    public void updateVotingRate(double votingRate) {
        this.votingCountRate = votingRate;
    }
}
