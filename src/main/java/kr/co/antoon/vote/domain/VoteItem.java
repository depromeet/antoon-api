package kr.co.antoon.vote.domain;

import kr.co.antoon.common.domain.BaseEntity;
import kr.co.antoon.vote.domain.vo.VoteCategory;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;


@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "vote_item")
public class VoteItem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private VoteCategory voteCategory;

    private String tags;

    private String title;

    private LocalDateTime votingEndTime;

    private Integer votingCount;

    private Integer votingCountPercent;

    private Integer joinCount;

    private Boolean voteStatus;

    public void updateJoinCount() {
        this.joinCount += 1;
    }
}
