package kr.co.antoon.feedback.application;

import kr.co.antoon.feedback.dto.FeedbackRequest;
import kr.co.antoon.feedback.domain.Feedback;
import kr.co.antoon.feedback.domain.vo.Score;
import kr.co.antoon.feedback.infrastructure.FeedbackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FeedbackService {
    private final FeedbackRepository feedbackRepository;

    @Transactional
    public Feedback create(FeedbackRequest request, Long userId) {
        var feedback = Feedback.builder()
                .content(request.content())
                .score(Score.of(request.score()))
                .userId(userId)
                .build();

        return feedbackRepository.save(feedback);
    }
}