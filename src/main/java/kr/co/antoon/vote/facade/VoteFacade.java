package kr.co.antoon.vote.facade;

import kr.co.antoon.coin.application.AntCoinService;
import kr.co.antoon.coin.domain.vo.RemittanceType;
import kr.co.antoon.vote.application.TopicService;
import kr.co.antoon.vote.application.CandidateService;
import kr.co.antoon.vote.application.VoteService;
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
    public void create(Long candidateId, Long userId) {
        var candidate = candidateService.findById(candidateId);
        var topic = topicService.findById(candidate.getTopicId());
        // 코인을 차감한다
        antCoinService.minusCoin(userId, VOTE_COIN, "CANDIDATE_ID_" + candidateId, RemittanceType.VOTE);

        // 투표 상태를 true로 변경
        voteService.save(userId, topic.getId(), candidate.getId(), true);
        // 후보의 득표율과 승패 여부를 업데이트
        candidateService.update(candidateId, userId);
        topic.updateJoinCount();
        topic.changeVoteStatus(true);
    }
}
