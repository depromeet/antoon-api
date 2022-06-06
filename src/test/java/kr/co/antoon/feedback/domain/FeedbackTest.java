package kr.co.antoon.feedback.domain;

import kr.co.antoon.feedback.domain.vo.Score;
import kr.co.antoon.feedback.domain.vo.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FeedbackTest {
    private Feedback feedback;

    @BeforeEach
    void setFeedback() {
        feedback = Feedback.builder()
                .content("피드백 테스트")
                .score(Score.ONE_STAR)
                .userId(1L)
                .build();
    }

    @Test
    void 상태_변경() {
        feedback.changeStatus(Status.COMPLETED);

        assertEquals(Status.COMPLETED, feedback.getStatus());
    }

    @Test
    void 진행중으로_상태_변경() {
        feedback.proceed();

        assertEquals(Status.PROCEED, feedback.getStatus());
    }

    @Test
    void 관리자_응답_진행() {
        feedback.reply("응답");

        assertEquals(Status.COMPLETED, feedback.getStatus());
    }
}