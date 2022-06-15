package kr.co.antoon.subject.infrastructure;

import kr.co.antoon.subject.domain.SubjectRecommendation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubjectRecommendationRepository extends JpaRepository<SubjectRecommendation, Long> {
    Optional<SubjectRecommendation> findByCharacterIdAndUserId(Long characterId, Long userId);
}
