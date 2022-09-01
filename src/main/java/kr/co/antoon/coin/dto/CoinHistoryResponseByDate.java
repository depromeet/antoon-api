package kr.co.antoon.coin.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record CoinHistoryResponseByDate(
        List<CoinHistoryResponse> coinHistoryResponses
) {

    public CoinHistoryResponseByDate(List<CoinHistoryResponse> coinHistoryResponses) {
        this.coinHistoryResponses = coinHistoryResponses;
    }
}