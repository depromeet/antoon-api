package kr.co.antoon.discussion.application;

import kr.co.antoon.discussion.domain.Discussion;
import kr.co.antoon.discussion.infrastructure.DiscussionRepository;
import kr.co.antoon.discussion.infrastructure.DiscussionLikeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@ExtendWith(MockitoExtension.class)
class DiscussionServiceTest {
    @Mock
    private DiscussionRepository discussionRepository;

    @Mock
    private DiscussionLikeRepository likeRepository;

    @InjectMocks
    private DiscussionService discussionService;

    private final Long MEMBER_ID = 1L;
    private final Long WEBTOON_ID = 1L;
    private final String CONTENT = "TEST CONTENT";
    private final Long DISCUSSION_ID = 1L;
    private final Long USER_ID = 1L;

    @Test
    public void 종목토론방_댓글_생성하기() {
        // given
        Discussion expected = Discussion.builder()
                .userId(MEMBER_ID)
                .webtoonId(WEBTOON_ID)
                .content(CONTENT)
                .build();

        Mockito.when(discussionRepository.save(any()))
                .thenReturn(expected);

        // when
        var actual = discussionService.save(MEMBER_ID, WEBTOON_ID, CONTENT);

        // then
        assertEquals(expected, actual);
    }

    @Test
    public void 종목토론방_댓글_상세조회() {
        // given
        Discussion expected1 = Discussion.builder()
                .userId(MEMBER_ID)
                .webtoonId(WEBTOON_ID)
                .content(CONTENT)
                .build();

        Mockito.when(discussionRepository.findById(anyLong()))
                .thenReturn(Optional.of(expected1));

        // when
        var actual = discussionService.findById(DISCUSSION_ID);

        // then
        assertEquals(
                expected1.getContent(),
                actual.getContent()
        );
    }

    @Test
    public void 댓글_작성시간_조회() {
        //given
        LocalDateTime t = LocalDateTime.now().minusSeconds(50);
        LocalDateTime t1 = LocalDateTime.now().minusMinutes(10);
        LocalDateTime t2 = LocalDateTime.now().minusHours(5);
        LocalDateTime t3 = LocalDateTime.now().minusDays(2);
        LocalDateTime t4 = LocalDateTime.now().minusMonths(7);
        LocalDateTime t5 = LocalDateTime.now().minusYears(3);

        //when
        var actual = discussionService.getTime(t);
        var actual1 = discussionService.getTime(t1);
        var actual2 = discussionService.getTime(t2);
        var actual3 = discussionService.getTime(t3);
        var actual4 = discussionService.getTime(t4);
        var actual5 = discussionService.getTime(t5);

        //then
        assertEquals(actual, "50초 전");
        assertEquals(actual1, "10분 전");
        assertEquals(actual2, "5시간 전");
        assertEquals(actual3, "2일 전");
        assertEquals(actual4, "7개월 전");
        assertEquals(actual5, "3년 전");
    }
}