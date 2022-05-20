package kr.co.antoon.webtoon.dto.response;

import java.util.List;

public record WebtoonGenreAllResponse (
        List<WebtoonGenrePreviewResponse> webtoons
){
    public record WebtoonGenrePreviewResponse(
            String genre,
            String thumbnail
    ){ }
}