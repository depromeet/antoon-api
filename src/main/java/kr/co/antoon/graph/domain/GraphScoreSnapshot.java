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
import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GraphScoreSnapshot extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int graphScore;

    private int scoreGap;

    private Long webtoonId;

    private LocalDateTime snapshotTime;

    @Enumerated(EnumType.STRING)
    private GraphStatus status;

    public GraphScoreSnapshot(int graphScore, int scoreGap, Long webtoonId, GraphStatus graphStatus) {
        this.graphScore = graphScore;
        this.scoreGap = scoreGap;
        this.webtoonId = webtoonId;
        this.snapshotTime = LocalDateTime.now();
        this.status = graphStatus;
    }

    public static GraphScoreSnapshot of(int graphScore, int scoreGap, Long webtoonId, GraphStatus graphStatus) {
        return switch (graphStatus) {
            case UP -> new GraphScoreSnapshot(graphScore, scoreGap, webtoonId, GraphStatus.UP);
            case DOWN -> new GraphScoreSnapshot(graphScore, scoreGap, webtoonId, GraphStatus.DOWN);
            default -> new GraphScoreSnapshot(graphScore, scoreGap, webtoonId, GraphStatus.MAINTAIN);
        };
    }


}