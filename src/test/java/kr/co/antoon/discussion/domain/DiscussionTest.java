package kr.co.antoon.discussion.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DiscussionTest {
    private Discussion discussion;

    @BeforeEach
    void discussion_setup() {
        discussion = Discussion.builder()
                .content("안녕하세요")
                .webtoonId(1L)
                .userId(1L)
                .build();
    }

    @Test
    void 좋아요_숫자_카운팅_UP() {
        discussion.updateLikeCount(true);
        int actual = discussion.getLikeCount();

        assertEquals(1, actual);
    }

    @Test
    void 좋아요_숫자가_0보다_클때만_감소_기능_동작() {
        discussion.updateLikeCount(false);
        int actual = discussion.getLikeCount();

        assertEquals(0, actual);
    }

    @Test
    void 좋아요_숫자_카운팅_DOWN() {
        discussion.updateLikeCount(true);
        assertEquals(1, discussion.getLikeCount());

        discussion.updateLikeCount(false);
        int actual = discussion.getLikeCount();

        assertEquals(0, actual);
    }
}