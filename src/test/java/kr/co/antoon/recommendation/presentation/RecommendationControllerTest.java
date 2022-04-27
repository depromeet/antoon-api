package kr.co.antoon.recommendation.presentation;

import kr.co.antoon.recommendation.domain.Recommendation;
import kr.co.antoon.recommendation.infrastructure.RecommendationRepository;
import kr.co.antoon.webtoon.domain.Webtoon;
import kr.co.antoon.webtoon.domain.vo.Platform;
import kr.co.antoon.webtoon.infrastructure.WebtoonRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class RecommendationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RecommendationRepository recommendationRepository;

    @Autowired
    private WebtoonRepository webtoonRepository;

    @Test
    @DisplayName("탑승해요 버튼 누름")
    void createJoinStatus() throws Exception {
        // given
        Webtoon mockWebtoon = webtoonRepository.save(Webtoon.builder()
                .title("여신강림")
                .content("여신강림입니다.")
                .url("http://www.naver.com")
                .thumbnail("http://www.naver.com")
                .platform(Platform.NAVER)
                .build());

        Recommendation mockRecommendation = recommendationRepository.save(
                Recommendation.builder()
                        .userId(1L)
                        .webtoonId(1L)
                        .build()
        );
        Long webtoonId = mockWebtoon.getId();
        log.info("webtoonId : {}", webtoonId);

        // when
        mockMvc.perform(patch("http://localhost:8080/api/v1/recommendations/join/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}