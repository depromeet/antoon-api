package kr.co.antoon.webtoon.dto.response;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import kr.co.antoon.webtoon.dto.WebtoonDto;

import java.util.List;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "@class")
public record WebtoonAgeResponse(
        List<WebtoonDto> webtoons
) { }
