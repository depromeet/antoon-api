package kr.co.antoon.vote.domain;

import kr.co.antoon.common.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Vote extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private Long topicId;

    private Long candidateId;

    private boolean voteStatus;

    @Builder
    public Vote(Long userId, Long topicId, Long candidateId, boolean voteStatus) {
        this.userId = userId;
        this.topicId = topicId;
        this.candidateId = candidateId;
        this.voteStatus = voteStatus;
    }

    public void updateVoteStatus(boolean voteStatus) {
        this.voteStatus = voteStatus;
    }
}
