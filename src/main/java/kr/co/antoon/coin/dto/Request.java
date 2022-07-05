package kr.co.antoon.coin.dto;

import kr.co.antoon.coin.domain.vo.RemittanceType;

public class Request {
    public record RewardReasonRequest(
            RemittanceType type,
            String id
    ) { }
}
