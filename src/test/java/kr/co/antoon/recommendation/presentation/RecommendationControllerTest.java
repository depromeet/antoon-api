package kr.co.antoon.recommendation.presentation;

import kr.co.antoon.error.exception.BusinessException;
import kr.co.antoon.recommendation.domain.Recommendation;
import kr.co.antoon.recommendation.domain.vo.RecommendationStatus;
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

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
public class RecommendationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RecommendationRepository recommendationRepository;

    @Autowired
    private WebtoonRepository webtoonRepository;

    @DisplayName("탑승해요 API - 성공")
    @Test
    void updateJoinStatus() throws Exception {
        // given
        Webtoon mockWebtoon = Webtoon.builder()
                .title("독립 일기")
                .content("처음으로 나만의 집이 생긴다면? 자까 작가의 나혼자 사는 이야기")
                .url("https://comic.naver.com/webtoon/list?titleId=748105&weekday=sun")
                .thumbnail("https://comic.naver.com/webtoon/list?titleId=748105&weekday=sun")
                .platform(Platform.NAVER)
                .build();

        webtoonRepository.save(mockWebtoon);
        Long webtoonId = mockWebtoon.getId();
        Long userId = 1L;

        // when
        mockMvc.perform(patch("/api/v1/recommendations/join/{userId}/{webtoonId}", userId, webtoonId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        // then
        Webtoon webtoon = webtoonRepository.findById(webtoonId).get();
        assertThat(webtoon.getJoinUserCount()).isEqualTo(1);

        Recommendation recommendation = recommendationRepository.findByUserIdAndWebtoonId(userId, webtoonId).get();
        assertThat(recommendation.getStatus()).isEqualTo(RecommendationStatus.JOINED);
    }

//    @DisplayName("탑승해요 API - 탑승 중인 경우 실패")
//    @Test
//    void failedUpdateStatus() throws Exception {
//        // given
//        Long webtoonId = 10L;
//        Long userId = 1L;
//
//        assertThrows(BusinessException.class, () -> updateJoinStatus(userId, webtoonId));
//    }
//
//    private void updateJoinStatus(Long userId, Long webtoonId) throws Exception {
//        mockMvc.perform(patch("/api/v1/recommendations/join/{userId}/{webtoonId}", userId, webtoonId)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isNoContent());
//    }


//    @DisplayName("다른 회원이 같은 웹툰의 탑승해요 버튼을 누를 경우 - 성공")
//    @Test
//    void updateJoinCount() throws Exception {
//        // given
//        Long webtoonId = webtoonRepository.findAll().get(0).getId();
//        log.info("webtoonId : {}", webtoonId);
//
//        Long userId = 0L;
//        userId++;
//
//        // when
//        mockMvc.perform(patch("/api/v1/recommendations/join/{userId}/{webtoonId}", userId, webtoonId)
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isNoContent());
//
//        // then
//        Webtoon webtoon = webtoonRepository.findById(webtoonId).get();
//        assertThat(webtoon.getJoinUserCount()).isEqualTo(2);
//    }
}