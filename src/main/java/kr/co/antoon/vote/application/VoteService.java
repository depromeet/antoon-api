package kr.co.antoon.vote.application;

import kr.co.antoon.vote.domain.Vote;
import kr.co.antoon.vote.infrastructure.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class VoteService {
    private final VoteRepository voteRepository;

    @Transactional
    public void save(Long userId, Long topicId, Long candidateId, boolean voteStatus) {
        var vote = new Vote(userId, topicId, candidateId, voteStatus);
        voteRepository.save(vote);
    }

    @Transactional(readOnly = true)
    public boolean existsByUserIdAndTopicId(Long userId, Long topicId) {
        return voteRepository.existsByUserIdAndTopicId(userId, topicId);
    }
}
