package kr.co.antoon.feedback.application;

import kr.co.antoon.feedback.domain.Feedback;
import kr.co.antoon.feedback.domain.vo.Score;
import kr.co.antoon.feedback.dto.request.FeedbackRequest;
import kr.co.antoon.feedback.infrastructure.FeedbackRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class FeedbackServiceTest {
    @Mock
    private FeedbackRepository feedbackRepository;

    @InjectMocks
    private FeedbackService feedbackService;

    @Test
    public void 피드백_생성() {
        FeedbackRequest request = new FeedbackRequest("피드백 본문", 1);
        Long useerId = 1L;

        var expected = Feedback.builder()
                .content("피드백 본문")
                .score(Score.ONE_STAR)
                .userId(1L)
                .build();

        Mockito.when(feedbackRepository.save(any()))
                .thenReturn(expected);

        Feedback actual = feedbackService.create(request, useerId);

        assertEquals(expected, actual);
    }
}
