package kr.co.antoon.feedback.facade;

import kr.co.antoon.feedback.application.FeedbackService;
import kr.co.antoon.feedback.domain.Feedback;
import kr.co.antoon.feedback.domain.vo.Score;
import kr.co.antoon.feedback.dto.request.FeedbackRequest;
import kr.co.antoon.feedback.dto.response.FeedbackResponse;
import kr.co.antoon.oauth.dto.AuthInfo;
import kr.co.antoon.user.application.UserService;
import kr.co.antoon.user.domain.vo.Role;
import kr.co.antoon.user.dto.response.UserDetailResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class FeedbackFacadeTest {
    @Mock
    private FeedbackService feedbackService;

    @Mock
    private UserService userService;

    @InjectMocks
    private FeedbackFacade feedbackFacade;

    @Test
    void 피드백_생성() {
        AuthInfo authInfo = new AuthInfo(1L, List.of(Role.USER));

        FeedbackRequest request = new FeedbackRequest("피드백 본문", 1);
        Long useerId = 1L;

        UserDetailResponse user = new UserDetailResponse(
                1L,
                "테스트",
                "test@gmail.com",
                "aslkdjalsdj",
                1
        );

        Feedback feedback = Feedback.builder()
                .content("피드백 본문")
                .score(Score.ONE_STAR)
                .userId(1L)
                .build();

        Mockito.when(userService.findById(authInfo.userId()))
                .thenReturn(user);

        Mockito.when(feedbackService.create(request, useerId))
                .thenReturn(feedback);

        FeedbackResponse expected = new FeedbackResponse(feedback, user);

        FeedbackResponse actual = feedbackFacade.create(request, authInfo);

        assertEquals(expected, actual);
    }
}
