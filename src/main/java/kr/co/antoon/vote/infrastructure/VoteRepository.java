package kr.co.antoon.vote.infrastructure;

import kr.co.antoon.vote.domain.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
    Vote findByTopicId(Long TopicId);
}
