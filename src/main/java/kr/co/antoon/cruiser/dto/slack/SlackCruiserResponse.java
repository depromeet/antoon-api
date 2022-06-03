package kr.co.antoon.cruiser.dto.slack;

import kr.co.antoon.common.util.TimeUtil;
import kr.co.antoon.feedback.dto.FeedbackResponse;

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
            long graphScoreSnapshotCount
    ) {
        return String.format("""
                        :deal-with-it: *%s 개미는 오늘도 알림!* :deal-with-it:
                                        
                        - 사용자 : %d건
                        - 종목토론 : %d건
                        - 종목토론 좋아요 : %d건
                        - 활성화된 웹툰 : %d건
                        - 비활성화된 웹툰 : %d건
                        - 전체 웹툰 : %d건
                        - 웹툰 스냅샷 [1일 기준] : %d건
                        - 그래프 스냅샷 [1시간 기준] : %d건
                        """,
                TimeUtil.now("yyyy-MM-dd HH:mm"),
                userCount,
                discussionCount,
                discussionLikeCount,
                publishWebtoonCount,
                pauseWebtoonCount,
                publishWebtoonCount + pauseWebtoonCount,
                webtoonSnapshotCount,
                graphScoreSnapshotCount
        );
    }

    public static String topRanks(
            List<String> topRanks
    ) {
        return topRanks.stream()
                .collect(Collectors.joining(
                        "",
                        ":fast-parrot: *개미가 좋아하는 웹툰* :fast-parrot:",
                        ""
                ));
    }

    public static String feedback(FeedbackResponse response) {
        return String.format("""
                        :crying-pepe: *개미야 빨리 수정해!!* :crying-pepe:
                                        
                        - 사용자 uid : %d
                        - 내용 : %s
                        - 점수 : %d
                        - 상태 : %s
                        """,
                response.userId(),
                response.content(),
                response.score().getWeight(),
                response.status().getDescription()
        );
    }
}