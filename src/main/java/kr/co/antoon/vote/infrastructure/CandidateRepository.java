package kr.co.antoon.vote.infrastructure;

import kr.co.antoon.vote.domain.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Long> {
    List<Candidate> findAllByTopicId(Long topicId);
}
