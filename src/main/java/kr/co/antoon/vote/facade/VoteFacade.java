package kr.co.antoon.vote.facade;

import kr.co.antoon.coin.application.AntCoinService;
import kr.co.antoon.coin.domain.vo.RemittanceType;
import kr.co.antoon.vote.application.TopicService;
import kr.co.antoon.vote.application.CandidateService;
import kr.co.antoon.vote.application.VoteService;
import kr.co.antoon.vote.domain.Vote;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class VoteFacade {
    private final TopicService topicService;
    private final CandidateService candidateService;
    private final VoteService voteService;
    private final AntCoinService antCoinService;

    private final static Long VOTE_COIN = 3L;

    @Transactional
    public void update(Long candidateId, Long userId) {
        antCoinService.minusCoin(userId, VOTE_COIN, "CANDIDATE_ID_" + candidateId, RemittanceType.VOTE);
        candidateService.update(candidateId, userId);

        var candidate = candidateService.findById(candidateId);
        var topic = topicService.findById(candidate.getTopicId());
        var vote = voteService.findByTopicId(candidate.getTopicId());
        topic.updateJoinCount();
        vote.updateVoteStatus();
    }
}
