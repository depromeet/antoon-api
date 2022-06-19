package kr.co.antoon.vote.infrastructure;

import kr.co.antoon.vote.domain.VoteSubject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoteSubjectRepository extends JpaRepository<VoteSubject, Long> {
    List<VoteSubject> findAllByVoteItemId(Long voteItemId);
}
