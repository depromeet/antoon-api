package kr.co.antoon.vote.domain;

import kr.co.antoon.common.domain.BaseEntity;
import lombok.AccessLevel;
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

    private Boolean voteStatus;

    public void updateVoteStatus(Boolean voteStatus) {
        this.voteStatus = voteStatus;
    }
}
