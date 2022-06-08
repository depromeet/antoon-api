package kr.co.antoon.coin.domain;

import kr.co.antoon.coin.domain.vo.AntCoinWalletStatus;
import kr.co.antoon.common.domain.BaseEntity;
import lombok.AccessLevel;
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
public class AntCoinWallet extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private Long wallet;

    @Enumerated(EnumType.STRING)
    private AntCoinWalletStatus status;

    public void plus(Long coin) {
        this.wallet += coin;
    }

    public boolean minus(Long coin) {
        if (this.wallet - coin < 0) {
            return false;
        }
        this.wallet -= coin;
        return true;
    }
}