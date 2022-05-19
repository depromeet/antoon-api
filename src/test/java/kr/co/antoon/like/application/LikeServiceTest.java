package kr.co.antoon.like.application;

import kr.co.antoon.discussion.application.DiscussionLikeService;
import kr.co.antoon.discussion.domain.Discussion;
import kr.co.antoon.discussion.domain.DiscussionLike;
import kr.co.antoon.discussion.infrastructure.DiscussionLikeRepository;
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
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class LikeServiceTest {

    @Mock
    private DiscussionLikeRepository likeRepository;

    @InjectMocks
    private DiscussionLikeService likeService;

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
                .userId(MEMBER_ID)
                .webtoonId(WEBTOON_ID)
                .content(CONTENT)
                .build();

        given(likeRepository.findByUserIdAndDiscussionId(anyLong(), anyLong())
        ).willReturn(
                Optional.empty()
        );

        // when & then
        assertDoesNotThrow(() -> {
            likeService.saveOrUpdate(mockDiscussion, USER_ID, DISCUSSION_ID);
        });
    }

    @Test
    @DisplayName("좋아요 업데이트 - 성공")
    void updateLike() {
        // given
        Discussion mockDiscussion = Discussion.builder()
                .userId(MEMBER_ID)
                .webtoonId(WEBTOON_ID)
                .content(CONTENT)
                .build();

        DiscussionLike mockLike = DiscussionLike.builder()
                .userId(USER_ID)
                .discussionId(DISCUSSION_ID)
                .build();

        given(likeRepository.findByUserIdAndDiscussionId(anyLong(), anyLong())
        ).willReturn(
                Optional.ofNullable(mockLike)
        );

        // when & then
        assertDoesNotThrow(() -> {
            likeService.saveOrUpdate(mockDiscussion, USER_ID, DISCUSSION_ID);
        });
    }

    @Test
    @DisplayName("좋아요 클릭 여부(좋아요 있는 경우) - 성공")
    void isUserLikeNotExist() {
        DiscussionLike mockLike = DiscussionLike.builder()
                .userId(USER_ID)
                .discussionId(DISCUSSION_ID)
                .build();

        given(likeRepository.findByUserIdAndDiscussionId(anyLong(), anyLong())
        ).willReturn(
                Optional.ofNullable(mockLike)
        );

        Boolean result = likeService.isUserLike(USER_ID, DISCUSSION_ID);
        assertThat(result).isEqualTo(true);
    }

    @Test
    @DisplayName("좋아요 클릭 여부(좋아요 없는 경우) - 성공")
    void isUserLike() {
        DiscussionLike mockLike = DiscussionLike.builder()
                .userId(USER_ID)
                .discussionId(DISCUSSION_ID)
                .build();

        given(likeRepository.findByUserIdAndDiscussionId(anyLong(), anyLong())
        ).willReturn(
                Optional.empty()
        );

        Boolean result = likeService.isUserLike(USER_ID, DISCUSSION_ID);
        assertThat(result).isEqualTo(false);
    }
}
