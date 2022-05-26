package kr.co.antoon.webtoon.dto.query;

import kr.co.antoon.webtoon.domain.vo.Platform;

public interface WebtoonGenreNativeDto {
    Long getWebtoonId();

    String getThumbnail();

    String getTitle();

    int getGraphScore();

    int getScoreGap();

    Platform getPlatform();
}