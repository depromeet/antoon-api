package kr.co.antoon.feedback.facade;

import kr.co.antoon.feedback.application.FeedbackService;
import kr.co.antoon.feedback.dto.FeedbackRequest;
import kr.co.antoon.feedback.dto.FeedbackResponse;
import kr.co.antoon.oauth.dto.AuthInfo;
import kr.co.antoon.user.application.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class FeedbackFacade {
    private final FeedbackService feedbackService;
    private final UserService userService;

    @Transactional
    public FeedbackResponse create(FeedbackRequest request, AuthInfo info) {
        var user = userService.findById(info.userId());

        var feedback = feedbackService.create(request, info.userId());

        return new FeedbackResponse(feedback, user);
    }
}