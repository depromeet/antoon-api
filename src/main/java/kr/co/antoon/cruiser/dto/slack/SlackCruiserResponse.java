package kr.co.antoon.cruiser.dto.slack;

import kr.co.antoon.common.util.TimeUtil;
import kr.co.antoon.feedback.dto.response.FeedbackResponse;

import java.util.List;
import java.util.stream.Collectors;

public class SlackCruiserResponse {
    public static String dataStatistics(
            long userCount,
            long discussionCount,
            long discussionLikeCount,
            long publishWebtoonCount,
            long pauseWebtoonCount,
            long webtoonSnapshotCount,
            long graphScoreSnapshotCount,
            long totalCoin
    ) {
        return String.format("""
                        :raised_hands: *%s 개미는 오늘도 알림!* :raised_hands:
                                        
                        - 사용자 : %d건
                        - 종목토론 : %d건
                        - 종목토론 좋아요 : %d건
                        - 활성화된 웹툰 : %d건
                        - 비활성화된 웹툰 : %d건
                        - 전체 웹툰 : %d건
                        - 웹툰 스냅샷 [1일 기준] : %d건
                        - 그래프 스냅샷 [1시간 기준] : %d건
                        - 사용자 Coin 총합 : %d
                        """,
                TimeUtil.now("yyyy-MM-dd HH:mm"),
                userCount,
                discussionCount,
                discussionLikeCount,
                publishWebtoonCount,
                pauseWebtoonCount,
                publishWebtoonCount + pauseWebtoonCount,
                webtoonSnapshotCount,
                graphScoreSnapshotCount,
                totalCoin
        );
    }

    public static String topRanks(
            List<String> topRanks
    ) {
        return topRanks.stream()
                .collect(Collectors.joining(
                        "",
                        ":raised_hands: *개미가 좋아하는 웹툰* :raised_hands:",
                        ""
                ));
    }

    public static String feedback(FeedbackResponse response) {
        return String.format("""
                        :raised_hands: *개미의 피드백!!* :raised_hands:
                                        
                        - 사용자 uid : %d
                        - 사용자 이름 : %s
                        - 내용 : %s
                        - 점수 : %d
                        - 상태 : %s
                        """,
                response.userId(),
                response.name(),
                response.content(),
                response.score().getWeight(),
                response.status().getDescription()
        );
    }
}
