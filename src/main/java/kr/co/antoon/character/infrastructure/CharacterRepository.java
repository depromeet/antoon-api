package kr.co.antoon.character.infrastructure;

import io.lettuce.core.dynamic.annotation.Param;
import kr.co.antoon.character.domain.Character;
import kr.co.antoon.character.domain.vo.CharacterType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CharacterRepository extends JpaRepository<Character, Long> {
    List<Character> findTop30ByTypeOrderByCoinAmountDesc(CharacterType type);

    @Query(value = """
        select ranking
        from (
        	select id, rank() over (order by coin_amount desc) as ranking
        	from characters
        ) ranked
        where ranked.id = :id
    """, nativeQuery = true)
    Integer findRankById(@Param(value = "id") Long id);
}
