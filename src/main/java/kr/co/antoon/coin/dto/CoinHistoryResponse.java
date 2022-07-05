package kr.co.antoon.coin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.co.antoon.coin.domain.vo.RemittanceStatus;
import kr.co.antoon.coin.domain.vo.RemittanceType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record CoinHistoryResponse(
        List<CoinHistory> history
) {
    public record CoinHistory(
            @Schema(description = "지급 날짜")
            LocalDate date,

            @Schema(description = "증가/감소")
            String status,

            @Schema(description = "코인 추가/감소 값")
            Long amount,

            @Schema(description = "총 코인 양")
            Long wallet,

            @Schema(description = "지급 종류")
            String remittanceType,

            @Schema(description = "지급 이유 / 웹툰 탑승 시 웹툰 id값")
            String reason
    ) {
        public CoinHistory(LocalDateTime createdAt, RemittanceStatus remittanceStatus, Long amount, Long wallet, RemittanceType remittanceType, String reason) {
            this(
                    createdAt.toLocalDate(),
                    remittanceStatus.toString(),
                    amount,
                    wallet,
                    remittanceType.getDescription(),
                    reason
            );
        }
    }
}

