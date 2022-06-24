package kr.co.antoon.vote.application;

import kr.co.antoon.error.dto.ErrorMessage;
import kr.co.antoon.error.exception.common.NotExistsException;
import kr.co.antoon.vote.domain.Candidate;
import kr.co.antoon.vote.domain.Topic;
import kr.co.antoon.vote.domain.vo.VoteResult;
import kr.co.antoon.vote.infrastructure.CandidateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CandidateService {
    private final CandidateRepository candidateRepository;

    @Transactional(readOnly = true)
    public List<Candidate> findAllByTopicId(Long topicId) {
        return candidateRepository.findAllByTopicId(topicId);
    }

    @Transactional
    public void update(Long candidateId, Topic topic) {
        Candidate candidate = candidateRepository.findById(candidateId)
                .orElseThrow(() -> new NotExistsException(ErrorMessage.NOT_EXISTS_CANDIDATE));

        topic.updateJoinCount();
        topic.changeVoteStatus(true);

        candidate.plusVotingCount();
        var votingRate = getVotingRate(topic, candidate);
        candidate.updateVotingRate(votingRate);

        updateVoteResult(topic);
    }

    private void updateVoteResult(Topic topic) {
        var candidates = candidateRepository.findAllByTopicId(topic.getId());
        Comparator<Candidate> comparatorByVotingRate = Comparator.comparingDouble(Candidate::getVotingCountRate);
        candidates.stream()
                .max(comparatorByVotingRate)
                .ifPresent(c -> c.updateVoteResult(VoteResult.WINNER));
    }

    private double getVotingRate(Topic topic, Candidate candidate) {
        return candidate.getVotingCount().doubleValue() / topic.getJoinCount().doubleValue();
    }

    @Transactional(readOnly = true)
    public Candidate findById(Long id) {
        return candidateRepository.findById(id)
                .orElseThrow(() -> new NotExistsException(ErrorMessage.NOT_EXISTS_CANDIDATE));
    }
}
