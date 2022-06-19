package kr.co.antoon.vote.domain;

import kr.co.antoon.common.domain.BaseEntity;
import kr.co.antoon.vote.domain.vo.VotingStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "vote_subject")
public class VoteSubject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long voteItemId;

    private String content;

    private String imageUrl;

    private Long votingCount;

    private Long votingRate;

    private Boolean votingFlag;

    @Enumerated(EnumType.STRING)
    private VotingStatus votingStatus;

    public void update(boolean votingFlag, VotingStatus votingStatus) {
        this.votingFlag = true;
        this.votingCount += 1;
        this.votingRate += 1;
        this.votingStatus = votingStatus;
    }
}
