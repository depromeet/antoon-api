package kr.co.antoon.webtoon.dto.query;

import kr.co.antoon.webtoon.domain.vo.GenreCategory;

public interface WebtoonGenreBannerNativeDto {
    GenreCategory getGenreCategory();

    String getThumbnail();
}