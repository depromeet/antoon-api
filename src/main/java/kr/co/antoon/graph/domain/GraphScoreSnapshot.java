package kr.co.antoon.graph.domain;

import kr.co.antoon.common.domain.BaseEntity;
import kr.co.antoon.graph.domain.vo.GraphStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import java.time.LocalDateTime;

@Getter
@Entity
/** @NOTICE not applied in real DB so set directly  */
@Table(indexes = @Index(name = "i_snapshot_time", columnList = "snapshotTime"))
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GraphScoreSnapshot extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int graphScore;

    private int scoreGap;

    private double scoreGapPercent;

    private Long webtoonId;

    private LocalDateTime snapshotTime;

    @Enumerated(EnumType.STRING)
    private GraphStatus status;

    public GraphScoreSnapshot(
            int graphScore,
            int scoreGap,
            double scoreGapPercent,
            Long webtoonId,
            GraphStatus graphStatus
    ) {
        this.graphScore = graphScore;
        this.scoreGap = scoreGap;
        this.scoreGapPercent = scoreGapPercent;
        this.webtoonId = webtoonId;
        this.snapshotTime = LocalDateTime.now();
        this.status = graphStatus;
    }

    public static GraphScoreSnapshot of(
            int graphScore,
            int scoreGap,
            double scoreGapPercent,
            Long webtoonId,
            GraphStatus graphStatus
    ) {
        return switch (graphStatus) {
            case UP -> new GraphScoreSnapshot(graphScore, scoreGap, scoreGapPercent, webtoonId, GraphStatus.UP);
            case DOWN -> new GraphScoreSnapshot(graphScore, scoreGap, scoreGapPercent, webtoonId, GraphStatus.DOWN);
            default -> new GraphScoreSnapshot(graphScore, scoreGap, scoreGapPercent, webtoonId, GraphStatus.MAINTAIN);
        };
    }
}
