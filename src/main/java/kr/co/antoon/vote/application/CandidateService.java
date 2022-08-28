package kr.co.antoon.vote.application;

import kr.co.antoon.error.exception.vote.NotExistsCandidateException;
import kr.co.antoon.vote.domain.Candidate;
import kr.co.antoon.vote.domain.Topic;
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
        var candidate = candidateRepository.findById(candidateId)
                .orElseThrow(NotExistsCandidateException::new);

        changeVotingStatus(topic);
        changeVotingRate(topic, candidate);
        updateWinner(topic);
    }

    private void changeVotingStatus(Topic topic) {
        topic.updateJoinCount();
        topic.changeVoteStatus(true);
    }

    private void changeVotingRate(Topic topic, Candidate candidate) {
        candidate.plusVotingCount();
        var candidates = candidateRepository.findAllByTopicId(topic.getId());
        for (Candidate c : candidates) {
            var votingRate = calculateVotingRate(topic, c);
            candidate.updateVotingRate(votingRate);
        }
    }

    private void updateWinner(Topic topic) {
        var candidates = candidateRepository.findAllByTopicId(topic.getId());
        Comparator<Candidate> comparatorByVotingRate = Comparator.comparingDouble(Candidate::getVotingCountRate);
        candidates.stream()
                .max(comparatorByVotingRate)
                .ifPresent(c -> c.updateWinner(true));
    }

    private double calculateVotingRate(Topic topic, Candidate candidate) {
        var voteRate = (candidate.getVotingCount().doubleValue() / topic.getJoinCount().doubleValue()) * 100;
        return Math.round((voteRate * 10) / 10.0);
    }

    @Transactional(readOnly = true)
    public Candidate findById(Long id) {
        return candidateRepository.findById(id)
                .orElseThrow(NotExistsCandidateException::new);
    }
}
