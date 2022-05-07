package kr.co.antoon.webtoon.dto.response;

import kr.co.antoon.webtoon.domain.Webtoon;

import java.util.List;

public record WebtoonResponse(
        List<WebtoonDetail> webtoons
) {
    public record WebtoonDetail(
            Long id,
            String title
    ) {
        public WebtoonDetail(Webtoon webtoon) {
            this(webtoon.getId(), webtoon.getTitle());
        }
    }
}