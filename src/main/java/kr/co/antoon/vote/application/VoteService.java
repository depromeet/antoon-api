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

    @Transactional(readOnly = true)
    public Vote findByTopicId(Long topicId) {
        return voteRepository.findByTopicId(topicId);
    }
}
