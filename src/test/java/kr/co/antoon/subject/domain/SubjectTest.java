package kr.co.antoon.subject.domain;

import kr.co.antoon.subject.domain.vo.SubjectType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SubjectTest {
    private Subject subject;

    @BeforeEach
    void setSubject() {
        subject = Subject.builder()
                .name("디프만 1번 출구")
                .title("인물/관계 테스트")
                .imageUrl("test.png")
                .type(SubjectType.CHARACTER)
                .webtoonId(1L)
                .build();
    }

    @Test
    void 인물_관계_코인_업데이트() {
        subject.amountUpdate(100L);
        assertEquals(100L, subject.getAmount());
    }
}
