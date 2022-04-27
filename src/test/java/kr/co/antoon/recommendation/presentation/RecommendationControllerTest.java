package kr.co.antoon.recommendation.presentation;

import kr.co.antoon.recommendation.infrastructure.RecommendationRepository;
import kr.co.antoon.webtoon.domain.Webtoon;
import kr.co.antoon.webtoon.domain.vo.Platform;
import kr.co.antoon.webtoon.infrastructure.WebtoonRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
public class RecommendationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RecommendationRepository recommendationRepository;

    @Autowired
    private WebtoonRepository webtoonRepository;

//    @Test
//    @DisplayName("탑슽해요 버튼 누름")
//    void createJoin() throws Exception {
//        // given
//        Webtoon mockWebtoon = Webtoon.builder()
//                .title("여신강림")
//                .content("여신강림입니다.")
//                .platform(Platform.NAVER)
//                .url("http://www.naver.com")
//                .thumbnail("http://www.naver.com")
//                .build();
//
//        System.out.println(mockWebtoon.getId());
//    }
}