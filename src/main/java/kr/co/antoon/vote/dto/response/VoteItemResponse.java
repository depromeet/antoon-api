package kr.co.antoon.vote.dto.response;

import kr.co.antoon.vote.domain.VoteItem;
import kr.co.antoon.vote.domain.VoteSubject;

import java.time.LocalDateTime;
import java.util.List;

public record VoteItemResponse(
        Long voteItemId,
        String tags,
        String title,
        LocalDateTime votingEndtime,
        Integer votingCount,
        Integer votingCountPercent,
        Integer joinCount,
        Boolean voteStatus,
        List<VoteSubject> voteSubjects
) {
    public VoteItemResponse(VoteItem voteItem, List<VoteSubject> voteSubjects) {
        this(
                voteItem.getId(),
                voteItem.getTags(),
                voteItem.getTitle(),
                voteItem.getVotingEndTime(),
                voteItem.getVotingCount(),
                voteItem.getVotingCountPercent(),
                voteItem.getJoinCount(),
                voteItem.getVoteStatus(),
                voteSubjects
        );
    }
}
