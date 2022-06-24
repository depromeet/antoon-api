package kr.co.antoon.coin.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record CoinHistory(
        @Schema(description = "사용자 Id")
        Long userId,

        @Schema(description = "사용자 지갑 Id")
        Long walletId,

        @Schema(description = "히스토리 날짜")
        LocalDateTime createdAt,

        @Schema(description = "코인 추가/감소 값")
        Long amount,

        @Schema(description = "증가/감소")
        String remittanceStatus,

        @Schema(description = "지급 종류")
        String remittanceType,

        @Schema(description = "지급 이유 / 웹툰 탑승 시 웹툰 id값")
        String reason
) { }
