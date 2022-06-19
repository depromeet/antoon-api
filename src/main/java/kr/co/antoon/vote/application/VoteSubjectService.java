package kr.co.antoon.vote.application;

import kr.co.antoon.error.dto.ErrorMessage;
import kr.co.antoon.error.exception.common.NotExistsException;
import kr.co.antoon.vote.domain.VoteSubject;
import kr.co.antoon.vote.domain.vo.VotingStatus;
import kr.co.antoon.vote.infrastructure.VoteSubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VoteSubjectService {
    private final VoteSubjectRepository voteSubjectRepository;

    public List<VoteSubject> findAllByVoteItemId(Long voteItemId) {
        return voteSubjectRepository.findAllByVoteItemId(voteItemId);
    }

    @Transactional
    public void update(Long voteSubjectId, Long userId) {
        VoteSubject voteSubject = voteSubjectRepository.findById(voteSubjectId)
                .orElseThrow(() -> new NotExistsException(ErrorMessage.NOT_EXISTS_VOTE_SUBJECT));

        VotingStatus votingStatus = voteSubject.getVotingStatus();
        voteSubject.update(true, votingStatus);
    }

    @Transactional(readOnly = true)
    public VoteSubject findById(Long id) {
        return voteSubjectRepository.findById(id)
                .orElseThrow(() -> new NotExistsException(ErrorMessage.NOT_EXISTS_VOTE_SUBJECT));
    }
}
