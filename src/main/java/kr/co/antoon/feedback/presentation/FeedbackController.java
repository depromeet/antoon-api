package kr.co.antoon.feedback.presentation;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.co.antoon.common.dto.ResponseDto;
import kr.co.antoon.cruiser.domain.Cruiser;
import kr.co.antoon.cruiser.dto.slack.SlackCruiserResponse;
import kr.co.antoon.feedback.application.FeedbackService;
import kr.co.antoon.feedback.dto.FeedbackRequest;
import kr.co.antoon.oauth.config.AuthUser;
import kr.co.antoon.oauth.dto.AuthInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static kr.co.antoon.common.util.CommonUtil.APPLICATION_JSON_UTF_8;

@Api(tags = "Feedback API")
@RestController
@RequestMapping(value = "/api/v1/feedback", produces = APPLICATION_JSON_UTF_8)
@RequiredArgsConstructor
public class FeedbackController {
    private final FeedbackService feedbackService;
    private final Cruiser cruiser;

    @ApiOperation(value = "Feedback 생성 API", notes = "*Slack Message 전송*")
    @PostMapping
    public ResponseEntity<Void> create(
            @RequestBody FeedbackRequest request,
            @AuthUser AuthInfo info
    ) {
        var response = feedbackService.create(request, info);
        var message = SlackCruiserResponse.feedback(response);

        cruiser.send(message);

        return ResponseDto.noContent();
    }
}