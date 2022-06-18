package kr.co.antoon.subject.application;

import kr.co.antoon.subject.domain.Subject;
import kr.co.antoon.subject.domain.vo.SubjectType;
import kr.co.antoon.subject.infrastructure.SubjectRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@ExtendWith(MockitoExtension.class)
public class SubjectServiceTest {
    @Mock
    private SubjectRepository subjectRepository;

    @InjectMocks
    private SubjectService subjectService;

    private final Long SUBJECT_ID = 1L;

    @Test
    public void 인물_관계_TOP30_조회() {
        // given
        var expected = new ArrayList<Subject>();
        for(int i = 0; i < 30; i++) {
            expected.add(Subject.builder()
                    .name("디프만 1번 출구")
                    .title("인물/관계 테스트")
                    .imageUrl("test.png")
                    .type(SubjectType.CHARACTER)
                    .webtoonId(1L)
                    .build()
            );
        }

        Mockito.when(subjectRepository.findTop30ByTypeOrderByAmountDesc(any()))
                .thenReturn(expected);

        // when
        List<Subject> actual = subjectService.getSubjectsByTopUpper(SubjectType.CHARACTER);
        // then
        assertEquals(expected, actual);
    }

    @Test
    public void 인물_관계_단일_조회() {
        // given
        var expected = Subject.builder()
                .name("디프만 1번 출구")
                .title("인물/관계 테스트")
                .imageUrl("test.png")
                .type(SubjectType.CHARACTER)
                .webtoonId(1L)
                .build();

         Mockito.when(subjectRepository.findById(anyLong()))
                 .thenReturn(Optional.of(expected));
         // when
         Subject actual = subjectService.findById(SUBJECT_ID);
         // then
         assertEquals(expected, actual);
    }
}
