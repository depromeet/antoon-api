package kr.co.antoon.graph.dto.response;

import kr.co.antoon.graph.domain.GraphScoreSnapshot;
import kr.co.antoon.graph.domain.vo.GraphStatus;

import java.time.LocalDateTime;
import java.util.List;

public record GraphScoreResponse(
        int count,
        List<GraphScoreDetail> graphScores
) {
    public GraphScoreResponse(List<GraphScoreDetail> graphScores) {
        this(graphScores.size(), graphScores);
    }

    public record GraphScoreDetail(
            int graphScore,
            int scoreGap,
            double scoreGapPercent,
            long webtoonId,
            LocalDateTime snapshotTime,
            GraphStatus status,
            String graphStatusDescription
    ) {
        public GraphScoreDetail(GraphScoreSnapshot snapshot) {
            this(
                    snapshot.getGraphScore(),
                    snapshot.getScoreGap(),
                    snapshot.getScoreGapPercent(),
                    snapshot.getWebtoonId(),
                    snapshot.getSnapshotTime(),
                    snapshot.getStatus(),
                    snapshot.getStatus().getDescription()
            );
        }
    }
}