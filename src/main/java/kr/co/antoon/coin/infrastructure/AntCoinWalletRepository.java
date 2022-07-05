package kr.co.antoon.coin.infrastructure;

import kr.co.antoon.coin.domain.AntCoinWallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AntCoinWalletRepository extends JpaRepository<AntCoinWallet, Long> {
    boolean existsByUserId(Long userId);

    Optional<AntCoinWallet> findByUserId(Long userId);
}
