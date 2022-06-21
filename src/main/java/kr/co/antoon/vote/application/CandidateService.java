package kr.co.antoon.vote.application;

import kr.co.antoon.error.dto.ErrorMessage;
import kr.co.antoon.error.exception.common.NotExistsException;
import kr.co.antoon.vote.domain.Candidate;
import kr.co.antoon.vote.domain.Topic;
import kr.co.antoon.vote.domain.vo.CandidateStatus;
import kr.co.antoon.vote.infrastructure.CandidateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CandidateService {
    private final CandidateRepository candidateRepository;

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

        // 특표율 공식 : A 득표율 / 유효표수
        double votingRate = candidate.getVotingCount().doubleValue() / topic.getJoinCount().doubleValue();
        // TODO: 후보의 승부를 가린다. (승리, 패배, 무승부)
        candidate.updateVotingRate(votingRate);
    }

    @Transactional(readOnly = true)
    public Candidate findById(Long id) {
        return candidateRepository.findById(id)
                .orElseThrow(() -> new NotExistsException(ErrorMessage.NOT_EXISTS_CANDIDATE));
    }
}
