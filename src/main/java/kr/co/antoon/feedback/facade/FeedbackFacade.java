package kr.co.antoon.feedback.facade;

import kr.co.antoon.cruiser.domain.CruiserClient;
import kr.co.antoon.cruiser.dto.slack.CruiserRequest;
import kr.co.antoon.cruiser.dto.slack.SlackCruiserResponse;
import kr.co.antoon.feedback.application.FeedbackService;
import kr.co.antoon.feedback.dto.request.FeedbackRequest;
import kr.co.antoon.feedback.dto.response.FeedbackResponse;
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
    private final CruiserClient cruiser;

    @Transactional
    public void create(FeedbackRequest request, AuthInfo info) {
        var user = userService.findById(info.userId());

        var feedback = feedbackService.create(request, info.userId());

        var feedbackResponse = new FeedbackResponse(feedback, user);

        var response = SlackCruiserResponse.feedback(feedbackResponse);

        cruiser.send(new CruiserRequest(response));
    }
}
