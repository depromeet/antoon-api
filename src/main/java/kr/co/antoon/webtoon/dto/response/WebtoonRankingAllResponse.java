package kr.co.antoon.webtoon.dto.response;

import java.util.List;

public record WebtoonRankingAllResponse (
        List<WebtoonRankingResponse> webtoons
) {
    public record WebtoonRankingResponse(
            String thumnail,
            String title,
            double scoreGap
    ) { }
}
