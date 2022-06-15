package kr.co.antoon.subject.application;

import kr.co.antoon.subject.domain.SubjectRecommendation;
import kr.co.antoon.subject.infrastructure.SubjectRecommendationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@ExtendWith(MockitoExtension.class)
public class SubjectRecommendationTest {
    @Mock
    private SubjectRecommendationRepository subjectRecommendationRepository;

    @InjectMocks
    private SubjectRecommendationService subjectRecommendationService;

    private final Long CHARACTER_ID = 1L;
    private final Long USER_ID = 1L;

    @Test
    public void 인물_관계_탑승하기() {
        // given
        var expected = new SubjectRecommendation(
                CHARACTER_ID,
                USER_ID
        );

        Mockito.when(subjectRecommendationRepository.save(any()))
                .thenReturn(expected);
        // when
        var actual = subjectRecommendationService.save(CHARACTER_ID, USER_ID);
        // then
        assertEquals(expected, actual);
    }

    @Test
    public void 인물_관계_탑승한_상태() {
        // given
        SubjectRecommendation subjectRecommendation = new SubjectRecommendation(
                CHARACTER_ID,
                USER_ID
        );

        Mockito.when(subjectRecommendationRepository.findByCharacterIdAndUserId(anyLong(), anyLong()))
                .thenReturn(Optional.of(subjectRecommendation));
        // when
        var actual = subjectRecommendationService.isUserJoin(CHARACTER_ID, USER_ID);
        // then
        assertEquals(true, actual);
    }

    @Test
    public void 인물_관계_탑승안한_상태() {
        // given
        Mockito.when(subjectRecommendationRepository.findByCharacterIdAndUserId(anyLong(), anyLong()))
                .thenReturn(Optional.empty());
        // when
        var actual = subjectRecommendationService.isUserJoin(CHARACTER_ID, USER_ID);
        // then
        assertEquals(false, actual);
    }
}
