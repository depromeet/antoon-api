package kr.co.antoon.webtoon.dto.response;

import kr.co.antoon.webtoon.domain.Webtoon;
import kr.co.antoon.webtoon.domain.vo.Platform;

import java.util.List;

public record WebtoonGenreResponse(
    List<WebtoonDetail> webtoons
) {
    public record WebtoonDetail(
            Long id,
            String title,
            String content,
            String webtoonUrl,
            String thumbnail,
            Platform platform
    ) {
        public WebtoonDetail(Webtoon webtoon) {
            this(webtoon.getId(), webtoon.getTitle(), webtoon.getContent(), webtoon.getWebtoonUrl(), webtoon.getThumbnail(), webtoon.getPlatform());
        }
    }
}
