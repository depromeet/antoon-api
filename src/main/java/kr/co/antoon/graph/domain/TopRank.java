package kr.co.antoon.graph.domain;

import kr.co.antoon.common.domain.BaseEntity;
import kr.co.antoon.graph.domain.vo.RankReason;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TopRank extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer ranking;

    private Long graphScoreSnapshotId;

    private Long webtoonId;

    @Enumerated(EnumType.STRING)
    private RankReason reason;

    private LocalDateTime rankTime;

    @Builder
    public TopRank(Integer ranking, Long graphScoreSnapshotId, RankReason reason, Long webtoonId) {
        this.ranking = ranking;
        this.graphScoreSnapshotId = graphScoreSnapshotId;
        this.reason = reason;
        this.webtoonId = webtoonId;
        this.rankTime = LocalDateTime.now();
    }
}
