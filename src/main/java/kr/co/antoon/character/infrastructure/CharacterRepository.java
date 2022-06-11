package kr.co.antoon.character.infrastructure;

import kr.co.antoon.character.domain.Character;
import kr.co.antoon.character.domain.vo.VoteType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CharacterRepository extends JpaRepository<Character, Long> {
    List<Character> findTop30ByTypeOrderByAmountDesc(VoteType type);
}
