package kr.co.antoon.coin.application;

import kr.co.antoon.coin.domain.AntCoinWallet;
import kr.co.antoon.coin.infrastructure.AntCoinWalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public AntCoinWallet get(Long userId) {
        return antCoinWalletRepository.getAntCoinWalletByUserId(userId);
    }
}