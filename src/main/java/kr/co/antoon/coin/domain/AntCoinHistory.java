package kr.co.antoon.coin.domain;

import kr.co.antoon.coin.domain.vo.RemittanceStatus;
import kr.co.antoon.coin.domain.vo.RemittanceType;
import kr.co.antoon.common.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AntCoinHistory extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private Long walletId;

    private Long amount;

    @Enumerated(EnumType.STRING)
    private RemittanceStatus remittanceStatus;

    @Enumerated(EnumType.STRING)
    private RemittanceType remittanceType;

    @Builder
    public AntCoinHistory(Long userId, Long walletId, Long amount, RemittanceStatus status, RemittanceType type) {
        this.userId = userId;
        this.walletId = walletId;
        this.amount = amount;
        this.remittanceStatus = status;
        this.remittanceType = type;
    }
}