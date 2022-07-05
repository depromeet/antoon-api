package kr.co.antoon.vote.facade;

import kr.co.antoon.coin.AntCoinClient;
import kr.co.antoon.coin.domain.vo.CoinRewardType;
import kr.co.antoon.coin.domain.vo.RemittanceType;
import kr.co.antoon.error.dto.ErrorMessage;
import kr.co.antoon.error.exception.common.AlreadyExistsException;
import kr.co.antoon.vote.application.CandidateService;
import kr.co.antoon.vote.application.TopicService;
import kr.co.antoon.vote.application.VoteService;
import kr.co.antoon.vote.domain.Candidate;
import kr.co.antoon.vote.domain.Topic;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class VoteFacade {
    private final TopicService topicService;
    private final CandidateService candidateService;
    private final VoteService voteService;
    private final AntCoinClient antCoinClient;

    @Transactional
    public void create(Long candidateId, Long userId) {
        var candidate = candidateService.findById(candidateId);
        var topic = topicService.findById(candidate.getTopicId());

        checkDuplicatedVote(userId, topic);
        useCoin(candidateId, userId);

        createVote(userId, candidate, topic);
        updateCandidateStatus(candidateId, topic);
    }

    @Transactional(readOnly = true)
    public void checkDuplicatedVote(Long userId, Topic topic) {
        if (voteService.existsByUserIdAndTopicId(userId, topic.getId())) {
            throw new AlreadyExistsException(ErrorMessage.ALREADY_VOTE_ERROR);
        }
    }

    private void createVote(Long userId, Candidate candidate, Topic topic) {
        voteService.save(userId, topic.getId(), candidate.getId(), true);
    }

    private void updateCandidateStatus(Long candidateId, Topic topic) {
        candidateService.update(candidateId, topic);
    }

    private void useCoin(Long candidateId, Long userId) {
        antCoinClient.minusCoin(
                userId,
                CoinRewardType.VOTE_USAGE_COIN.getAmount(),
                candidateId.toString(),
                RemittanceType.VOTE
        );
    }
}
