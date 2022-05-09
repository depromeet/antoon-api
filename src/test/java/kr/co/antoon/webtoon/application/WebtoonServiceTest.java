//package kr.co.antoon.webtoon.application;
//
//import kr.co.antoon.webtoon.domain.Webtoon;
//import kr.co.antoon.webtoon.domain.WebtoonGenre;
//import kr.co.antoon.webtoon.domain.vo.ActiveStatus;
//import kr.co.antoon.webtoon.domain.vo.GenreCategory;
//import kr.co.antoon.webtoon.domain.vo.Platform;
//import kr.co.antoon.webtoon.infrastructure.WebtoonGenreRepository;
//import kr.co.antoon.webtoon.infrastructure.WebtoonRepository;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.util.List;
//
//@Slf4j
//@SpringBootTest
//@AutoConfigureMockMvc
//public class WebtoonServiceTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private WebtoonService webtoonService;
//
//    @Autowired
//    private WebtoonGenreService webtoonGenreService;
//
//    @Autowired
//    private WebtoonRepository webtoonRepository;
//
//    @Autowired
//    private WebtoonGenreRepository webtoonGenreRepository;
//
//    @DisplayName("장르별 웹툰 조회 - 성공")
//    @Test
//    void 장르별_웹툰_조회() {
//       // given
//        Webtoon expected = Webtoon.builder()
//                .title("독립 일기")
//                .content("처음으로 나만의 집이 생긴다면? 자까 작가의 나혼자 사는 이야기")
//                .webtoonUrl("https://comic.naver.com/webtoon/list?titleId=748105&weekday=sun")
//                .thumbnail("https://comic.naver.com/webtoon/list?titleId=748105&weekday=sun")
//                .platform(Platform.NAVER)
//                .platform(Platform.NAVER)
//                .build();
//
//        // given
//        webtoonGenreRepository.save(new WebtoonGenre(GenreCategory.of("ACTION"), expected.getId()));
//    }
//}