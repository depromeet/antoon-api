package kr.co.antoon.coin.domain;

import kr.co.antoon.coin.domain.vo.CoinRank;
import kr.co.antoon.coin.domain.vo.CoinRankCriteria;
import kr.co.antoon.coin.domain.vo.WalletStatus;
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
import javax.persistence.Transient;

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
    private WalletStatus status;

    @Enumerated(EnumType.STRING)
    private CoinRank coinRank;

    @Transient
    private final static Long DEFAULT_COIN = 0L;

    public AntCoinWallet(Long userId) {
        this.userId = userId;
        this.wallet = DEFAULT_COIN;
        this.status = WalletStatus.ENABLE;
        this.coinRank = CoinRank.LEVEL_TWO;
    }

    public void changeStatus(WalletStatus status) {
        this.status = status;
    }

    public void enable() {
        changeStatus(WalletStatus.ENABLE);
    }

    public void disable() {
        changeStatus(WalletStatus.DISABLE);
    }

    public void plus(Long coin) {
        this.wallet += coin;
        setCoinRank();
    }

    public boolean minus(Long coin) {
        if (this.wallet - coin < 0) {
            return false;
        }
        this.wallet -= coin;
        setCoinRank();
        return true;
    }

    public CoinRank checkCoinRank() {
        if (this.wallet < CoinRankCriteria.LEVEL_TWO.getCoinCriteria()) {
            return CoinRank.LEVEL_ONE;
        }
        if (this.wallet < CoinRankCriteria.LEVEL_THREE.getCoinCriteria()) {
            return CoinRank.LEVEL_TWO;
        }
        return CoinRank.LEVEL_THREE;
    }

    public void setCoinRank() {
        this.coinRank = checkCoinRank();
    }
}
