package kr.co.antoon.vote.facade;

import kr.co.antoon.vote.application.VoteItemService;
import kr.co.antoon.vote.application.VoteSubjectService;
import kr.co.antoon.vote.dto.response.VoteItemResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class VoteItemFacade {
    private final VoteItemService voteItemService;
    private final VoteSubjectService voteSubjectService;

    @Transactional(readOnly = true)
    public VoteItemResponse findVoteItemById(Long voteItemId) {
        var voteItem = voteItemService.findById(voteItemId);
        var voteSubjects = voteSubjectService.findAllByVoteItemId(voteItemId);
        return new VoteItemResponse(voteItem, voteSubjects);
    }
}
