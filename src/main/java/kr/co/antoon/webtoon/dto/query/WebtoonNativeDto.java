package kr.co.antoon.webtoon.dto.query;

import kr.co.antoon.webtoon.domain.vo.ActiveStatus;
import kr.co.antoon.webtoon.domain.vo.GenreCategory;
import kr.co.antoon.webtoon.domain.vo.Platform;

public interface WebtoonNativeDto {
    Long getWebtoonId();

    String getTitle();

    String getContent();

    String getWebtoonUrl();

    String getThumbnail();

    Platform getPlatform();

    ActiveStatus getStatus();

    Long getWebtoonGenreId();

    GenreCategory getGenreCategory();

    Long getWebtoonPublishDayId();

    String getDay();

    Long getWebtoonWriterId();

    String getName();

    Long getRecommendationCountId();

    Integer getJoinCount();

    Integer getLeaveCount();
}