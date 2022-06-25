package kr.co.antoon.feedback.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.antoon.cruiser.domain.Cruiser;
import kr.co.antoon.feedback.domain.vo.Score;
import kr.co.antoon.feedback.domain.vo.Status;
import kr.co.antoon.feedback.dto.request.FeedbackRequest;
import kr.co.antoon.feedback.dto.response.FeedbackResponse;
import kr.co.antoon.feedback.facade.FeedbackFacade;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(useDefaultFilters = false)
@AutoConfigureMockMvc(addFilters = false)
@Import(FeedbackController.class)
@ExtendWith(MockitoExtension.class)
class FeedbackControllerTest {
    @MockBean
    private FeedbackFacade feedbackFacade;

    @MockBean
    private Cruiser cruiser;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void 피드백_생성() throws Exception {
        FeedbackRequest request = new FeedbackRequest(
                "content",
                1
        );

        FeedbackResponse response = new FeedbackResponse(
                1L,
                "content",
                Score.ONE_STAR,
                Status.COMPLETED,
                1L,
                "name",
                "email",
                26,
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        Mockito.when(feedbackFacade.create(any(), any()))
                .thenReturn(response);

        mockMvc.perform(
                post("/api/v1/feedback")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(objectMapper.writeValueAsString(request))
        ).andExpect(status().isNoContent());
    }
}
