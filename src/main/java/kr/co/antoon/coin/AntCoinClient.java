package kr.co.antoon.coin;


import kr.co.antoon.coin.domain.AntCoinWallet;
import kr.co.antoon.coin.domain.vo.RemittanceType;
import kr.co.antoon.coin.dto.CoinHistory;

/**
 * @apiNote coin module
 **/
public interface AntCoinClient {
    /**
     * @param coin   amount of coins to add
     * @param reason reason of coins to add
     * @param type   PLUS
     * @apiNote add coin from user's wallet
     **/
    void plusCoin(Long userId, Long coin, String reason, RemittanceType type);

    /**
     * @param coin   amount of coins to sub
     * @param reason reason of coins to sub
     * @param type   MINUS
     * @apiNote sub coin from user's wallet
     **/
    void minusCoin(Long userId, Long coin, String reason, RemittanceType type);

    /**
     * @apiNote select user's wallet
     **/
    AntCoinWallet getWallet(Long userId);

    /**
     * @apiNote select user's coin add/sub
     **/
    CoinHistory getCoinHistory(Long userId);

    /**
     * @apiNote add coin when sign up
     **/
    void sign(Long userId);
}
