package kr.co.antoon.coin.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record CoinReason(
        @Schema(description = "웹툰 Id")
        Integer webtoonId
) {}
