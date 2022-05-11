package kr.co.antoon.cruiser.dto.slack;

import kr.co.antoon.common.Utility;

public class SlackCruiserResponse {
    public static String dataStatistics(
            long userCount,
            long discussionCount,
            long publishWebtoonCount,
            long pauseWebtoonCount,
            long webtoonSnapshotCount
    ) {
        return String.format("""
                        :deal-with-it: *%s 개미는 오늘도 알림!* :deal-with-it:
                                        
                        사용자 : %d건
                        종목토론 : %d건
                        활성화된 웹툰 : %d건
                        비활성화된 웹툰 : %d건
                        전체 웹툰 : %d건
                        웹툰 스냅샷 : %d건
                            
                        """,
                Utility.now("yyyy-MM-dd HH:mm"),
                userCount,
                discussionCount,
                publishWebtoonCount,
                pauseWebtoonCount,
                publishWebtoonCount + pauseWebtoonCount,
                webtoonSnapshotCount
        );
    }
}