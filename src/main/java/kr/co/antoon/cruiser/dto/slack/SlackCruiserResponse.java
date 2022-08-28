package kr.co.antoon.cruiser.dto.slack;

import kr.co.antoon.common.util.TimeUtil;
import kr.co.antoon.feedback.dto.response.FeedbackResponse;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

public class SlackCruiserResponse {

    @RequiredArgsConstructor
    public static class DataStatisticsResponse implements CruiserResponse {
        private final long userCount;
        private final long discussionCount;
        private final long discussionLikeCount;
        private final long publishWebtoonCount;
        private final long pauseWebtoonCount;
        private final long webtoonSnapshotCount;
        private final long graphScoreSnapshotCount;
        private final long totalCoin;

        @Override
        public String message() {
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
    }

    @RequiredArgsConstructor
    public static class TopRanksResponse implements CruiserResponse {
        private final List<String> topRanks;

        @Override
        public String message() {
            return topRanks.stream()
                    .collect(Collectors.joining(
                            "",
                            ":raised_hands: *개미가 좋아하는 웹툰* :raised_hands:",
                            ""
                    ));
        }
    }

    @RequiredArgsConstructor
    public static class FeedbackStaticsResponse implements CruiserResponse {
        private final FeedbackResponse response;

        @Override
        public String message() {
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
}
