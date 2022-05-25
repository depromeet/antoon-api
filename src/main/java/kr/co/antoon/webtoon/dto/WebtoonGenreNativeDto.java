package kr.co.antoon.webtoon.dto;

import kr.co.antoon.webtoon.domain.vo.GenreCategory;

public interface WebtoonGenreNativeDto {
    GenreCategory getGenreCategory();

    String getThumbnail();
}