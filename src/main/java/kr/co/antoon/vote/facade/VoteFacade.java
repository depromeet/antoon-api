package kr.co.antoon.vote.facade;

import kr.co.antoon.coin.application.AntCoinService;
import kr.co.antoon.coin.domain.vo.RemittanceType;
import kr.co.antoon.vote.application.VoteItemService;
import kr.co.antoon.vote.application.VoteSubjectService;
import kr.co.antoon.vote.domain.VoteItem;
import kr.co.antoon.vote.domain.VoteSubject;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class VoteFacade {
    private final VoteItemService voteItemService;
    private final VoteSubjectService voteSubjectService;
    private final AntCoinService antCoinService;

    private final static Long VOTE_COIN = 3L;

    @Transactional
    public void update(Long voteSubjectId, Long userId) {
        antCoinService.minusCoin(userId, VOTE_COIN, "VOTE_SUBJECT_ID_" + voteSubjectId, RemittanceType.VOTE);
        voteSubjectService.update(voteSubjectId, userId);
        var voteSubject = voteSubjectService.findById(voteSubjectId);
        var voteItem = voteItemService.findById(voteSubject.getVoteItemId());
        voteItem.updateJoinCount();
    }
}
