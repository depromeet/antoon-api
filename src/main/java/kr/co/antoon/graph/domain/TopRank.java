package kr.co.antoon.graph.domain;

import kr.co.antoon.graph.domain.vo.RankReason;
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
public class TopRank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer ranking;

    private Long graphScoreSnapshotId;

    @Enumerated(EnumType.STRING)
    private RankReason reason;

    public TopRank(Integer ranking, Long graphScoreSnapshotId, RankReason reason) {
        this.ranking = ranking;
        this.graphScoreSnapshotId = graphScoreSnapshotId;
        this.reason = reason;
    }
}