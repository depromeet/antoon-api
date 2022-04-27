package kr.co.antoon.like.application;

import kr.co.antoon.discussion.domain.Discussion;
import kr.co.antoon.discussion.infrastructure.DiscussionRepository;
import kr.co.antoon.error.exception.common.NotExistsException;
import kr.co.antoon.like.domain.Like;
import kr.co.antoon.like.infrastructure.LikeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class LikeServiceTest {

    @Mock
    private LikeRepository likeRepository;

    @Mock
    private DiscussionRepository discussionRepository;

    @InjectMocks
    private LikeService likeService;

    private final Long MEMBER_ID = 1L;
    private final Long WEBTOON_ID = 1L;
    private final String CONTENT = "TEST CONTENT";
    private final Long DISCUSSION_ID = 1L;
    private final Long USER_ID = 1L;

    @Test
    @DisplayName("좋아요 생성 - 성공")
    void createLike() {
        // given
        Discussion mockDiscussion = Discussion.builder()
                .memberId(MEMBER_ID)
                .webtoonId(WEBTOON_ID)
                .content(CONTENT)
                .build();

        given(discussionRepository.findById(anyLong())
        ).willReturn(
                Optional.ofNullable(mockDiscussion)
        );

        given(likeRepository.findByUserIdAndDiscussionId(anyLong(), anyLong())
        ).willReturn(
                Optional.empty()
        );

        // when & then
        assertDoesNotThrow(() -> {
            likeService.saveOrUpdate(USER_ID, DISCUSSION_ID);
        });
    }

    @Test
    @DisplayName("좋아요 업데이트 - 성공")
    void updateLike() {
        // given
        Discussion mockDiscussion = Discussion.builder()
                .memberId(MEMBER_ID)
                .webtoonId(WEBTOON_ID)
                .content(CONTENT)
                .build();

        Like mockLike = Like.builder()
                .userId(USER_ID)
                .discussionId(DISCUSSION_ID)
                .build();

        given(discussionRepository.findById(anyLong())
        ).willReturn(
                Optional.ofNullable(mockDiscussion)
        );

        given(likeRepository.findByUserIdAndDiscussionId(anyLong(), anyLong())
        ).willReturn(
                Optional.ofNullable(mockLike)
        );

        // when & then
        assertDoesNotThrow(() -> {
            likeService.saveOrUpdate(USER_ID, DISCUSSION_ID);
        });
    }

    @Test
    @DisplayName("좋아요 생성/업데이트 - 실패(존재 하지 않는 댓글)")
    void likeFailBecauseNotExistDiscussion() {
        // given
        given(discussionRepository.findById(anyLong())
        ).willReturn(
                Optional.empty()
        );

        // when & then
        assertThrows(NotExistsException.class, () -> {
           likeService.saveOrUpdate(USER_ID, DISCUSSION_ID);
        });
    }
}