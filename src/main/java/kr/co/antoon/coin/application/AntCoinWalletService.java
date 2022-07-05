package kr.co.antoon.coin.application;

import kr.co.antoon.coin.domain.AntCoinWallet;
import kr.co.antoon.coin.infrastructure.AntCoinWalletRepository;
import kr.co.antoon.error.dto.ErrorMessage;
import kr.co.antoon.error.exception.common.NotExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AntCoinWalletService {
    private final AntCoinWalletRepository antCoinWalletRepository;

    @Transactional
    public AntCoinWallet save(Long userId) {
        var wallet = new AntCoinWallet(userId);
        return antCoinWalletRepository.save(wallet);
    }

    @Transactional(readOnly = true)
    public boolean existsByUserId(Long userId) {
        return antCoinWalletRepository.existsByUserId(userId);
    }

    @Transactional(readOnly = true)
    public AntCoinWallet findByUserId(Long userId) {
        return antCoinWalletRepository.findByUserId(userId)
                .orElseThrow(() -> new NotExistsException(ErrorMessage.NOT_EXISTS_ANT_COIN_WALLET));
    }

    @Transactional(readOnly = true)
    public List<AntCoinWallet> findAll() {
        return antCoinWalletRepository.findAll();
    }
}
