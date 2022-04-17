package kr.co.antoon.graph.domain;

import kr.co.antoon.common.domain.BaseEntity;
import kr.co.antoon.graph.domain.vo.GraphStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GraphScoreSnapshot extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double graphScore;

    private Long webtoonId;

    private LocalDateTime snapshotTime;

    @Enumerated(EnumType.STRING)
    private GraphStatus graphStatus;

    public GraphScoreSnapshot(Double graphScore, Long webtoonId, GraphStatus graphStatus) {
        this.graphScore = graphScore;
        this.webtoonId = webtoonId;
        this.snapshotTime = LocalDateTime.now();
        this.graphStatus = graphStatus;
    }

    public static GraphScoreSnapshot of(Double graphScore, Long webtoonId, GraphStatus graphStatus) {
        return switch (graphStatus) {
            case UP -> new GraphScoreSnapshot(graphScore, webtoonId, GraphStatus.UP);
            case DOWN -> new GraphScoreSnapshot(graphScore, webtoonId, GraphStatus.DOWN);
            default -> new GraphScoreSnapshot(graphScore, webtoonId, GraphStatus.MAINTATIN);
        };
    }
}