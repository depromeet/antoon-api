package kr.co.antoon.coin;


import kr.co.antoon.coin.domain.AntCoinWallet;
import kr.co.antoon.coin.domain.vo.RemittanceType;
import kr.co.antoon.coin.dto.CoinHistory;

public interface AntCoinClient {
    void plusCoin(Long userId, Long coin, String reason, RemittanceType type);
    void minusCoin(Long userId, Long coin, String reason, RemittanceType type);
    AntCoinWallet getWallet(Long userId);
    CoinHistory getCoinHistory(Long userId);
    void sign(Long userId);
}
