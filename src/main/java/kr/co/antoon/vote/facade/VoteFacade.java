package kr.co.antoon.vote.facade;

import kr.co.antoon.coin.application.AntCoinService;
import kr.co.antoon.coin.domain.vo.CoinUsageType;
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
    public static final String CANDIDATE_ID = "CANDIDATE_ID_";
    private final TopicService topicService;
    private final CandidateService candidateService;
    private final VoteService voteService;
    private final AntCoinService antCoinService;

    private final static Long VOTE_COIN = 3L;

    @Transactional
    public void create(Long candidateId, Long userId) {
        var candidate = candidateService.findById(candidateId);
        var topic = topicService.findById(candidate.getTopicId());

        // 투표를 하면 사용자 코인을 차감
        antCoinService.minusCoin(userId, CoinUsageType.VOTED_TOPIC.getAmount(), CANDIDATE_ID + candidateId, RemittanceType.VOTE);

        // 투표 여부를 true로 변경
        voteService.save(userId, topic.getId(), candidate.getId(), true);

        // 토픽의 총 참여 수, 토픽의 투표 완료 여부, 투표한 후보의 득표 수, 득표율, 투표 결과를 업데이트
        candidateService.update(candidateId, topic);
    }
}
