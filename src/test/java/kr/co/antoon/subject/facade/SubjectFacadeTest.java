package kr.co.antoon.subject.facade;

import kr.co.antoon.subject.application.SubjectRecommendationService;
import kr.co.antoon.subject.application.SubjectService;
import kr.co.antoon.subject.domain.Subject;
import kr.co.antoon.subject.domain.vo.SubjectType;
import kr.co.antoon.subject.dto.response.CharacterResponse;
import kr.co.antoon.subject.dto.response.CoupleResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@ExtendWith(MockitoExtension.class)
public class SubjectFacadeTest {
    @Mock
    private SubjectService subjectService;

    @Mock
    private SubjectRecommendationService subjectRecommendationService;

    @InjectMocks
    private SubjectFacade subjectFacade;

    private final Long USER_ID = 1L;

    @Test
    public void 인물_TOP30_조회() {
        // given
        var subjects = new ArrayList<Subject>();
        for(int i = 0; i < 30; i++) {
            subjects.add(Subject.builder()
                    .name("디프만 1번 출구")
                    .title("인물 테스트")
                    .imageUrl("test.png")
                    .type(SubjectType.CHARACTER)
                    .webtoonId(1L)
                    .build()
            );
        }

        Mockito.when(subjectService.getSubjectsByTopUpper(SubjectType.CHARACTER))
                .thenReturn(subjects);

        Mockito.when(subjectRecommendationService.isUserJoin(any(), anyLong()))
                .thenReturn(false);

        var responses = new ArrayList<CharacterResponse.CharacterDetailResponse>();
        for(int i = 0; i < 30; i++) {
            responses.add(new CharacterResponse.CharacterDetailResponse(
                    "디프만 1번 출구",
                    "인물 테스트",
                    "test.png",
                    0L,
                    false
            ));
        }
        var expected = new CharacterResponse(responses);
        // when
        var actual = subjectFacade.getCharactersByTopUpper(USER_ID);
        // then
        assertEquals(expected, actual);
    }

    @Test
    public void 커플_TOP30_조회() {
        // given
        var subjects = new ArrayList<Subject>();
        for(int i = 0; i < 30; i++) {
            subjects.add(Subject.builder()
                    .name("디프만 1번 출구,디프만 2번 출구")
                    .title("관계 테스트")
                    .imageUrl("test1.png,test2.png")
                    .type(SubjectType.COUPLE)
                    .webtoonId(1L)
                    .build()
            );
        }

        Mockito.when(subjectService.getSubjectsByTopUpper(SubjectType.COUPLE))
                .thenReturn(subjects);

        Mockito.when(subjectRecommendationService.isUserJoin(any(), anyLong()))
                .thenReturn(false);

        var responses = new ArrayList<CoupleResponse.CoupleDetailResponse>();
        for(int i = 0; i < 30; i++) {
            responses.add(new CoupleResponse.CoupleDetailResponse(
                    subjects.get(i),
                    false
            ));
        }
        var expected = new CoupleResponse(responses);
        // when
        var actual = subjectFacade.getCouplesByTopUpper(USER_ID);
        // then
        assertEquals(expected, actual);
    }
}
