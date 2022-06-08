package kr.co.antoon.coin.infrastructure;

import kr.co.antoon.coin.domain.AntCoinHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AntCoinHistoryRepository extends JpaRepository<AntCoinHistory, Long> {
}