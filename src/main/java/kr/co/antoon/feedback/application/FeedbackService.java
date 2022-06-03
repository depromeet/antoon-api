package kr.co.antoon.feedback.application;

import kr.co.antoon.feedback.dto.FeedbackRequest;
import kr.co.antoon.feedback.dto.FeedbackResponse;
import kr.co.antoon.feedback.entity.Feedback;
import kr.co.antoon.feedback.entity.vo.Score;
import kr.co.antoon.feedback.infrastructure.FeedbackRepository;
import kr.co.antoon.oauth.dto.AuthInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FeedbackService {
    private final FeedbackRepository feedbackRepository;

    @Transactional
    public FeedbackResponse create(FeedbackRequest request, AuthInfo info) {
        var feedback = Feedback.builder()
                .content(request.content())
                .score(Score.of(request.score()))
                .userId(info.userId())
                .build();

        var response = feedbackRepository.save(feedback);

        return new FeedbackResponse(response);
    }
}