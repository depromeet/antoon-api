package kr.co.antoon.discussion.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class DiscussionLikeTest {
    private DiscussionLike discussionLike;

    @BeforeEach
    void setDiscussionLike() {
        discussionLike = DiscussionLike
                .builder()
                .userId(1L)
                .discussionId(1L)
                .build();
    }

    @Test
    void 종토방_좋아요_생성시_true() {
        assertEquals(true, discussionLike.getStatus());
    }

    @Test
    void 종토방_좋아요_업데이트() {
        DiscussionLike actual = discussionLike.update();
        assertFalse(actual.getStatus());
    }
}