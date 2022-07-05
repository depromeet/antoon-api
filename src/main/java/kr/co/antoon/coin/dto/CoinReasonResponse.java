package kr.co.antoon.coin.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record CoinReasonResponse(
        @Schema(description = "코인 지급 원인 Id")
        Long Id
) { }
