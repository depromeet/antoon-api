package kr.co.antoon.like.infrastructure;


import kr.co.antoon.discussion.domain.Discussion;
import kr.co.antoon.discussion.infrastructure.DiscussionRepository;
import kr.co.antoon.discussion.domain.DiscussionLike;
import kr.co.antoon.discussion.infrastructure.DiscussionLikeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class LikeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DiscussionLikeRepository likeRepository;

    @Autowired
    private DiscussionRepository discussionRepository;

    private final Long MEMBER_ID = 1L;
    private final Long WEBTOON_ID = 1L;
    private final String CONTENT = "TEST CONTENT";
    private final Long USER_ID = 1L;

    @Test
    @DisplayName("좋아요 생성 - 성공")
    void createLike() throws Exception {
        // given
        Discussion mockDiscussion = discussionRepository.save(
                Discussion.builder()
                .memberId(MEMBER_ID)
                .webtoonId(WEBTOON_ID)
                .content(CONTENT)
                .build()
        );
        Long discussionId = mockDiscussion.getId();
        // when
        mockMvc.perform(put("/api/v1/webtoons/discussions/" + discussionId + "/likes")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        // then
        Discussion discussion = discussionRepository.findById(discussionId).get();
        assertThat(discussion.getLikeCount()).isEqualTo(1);

        DiscussionLike like = likeRepository.findById(1L).get();
        assertThat(like.getFlag()).isTrue();
    }

    @Test
    @DisplayName("좋아요 업데이트 - 성공")
    void updateLike() throws Exception {
        // given
        Discussion mockDiscussion = discussionRepository.save(
                Discussion.builder()
                        .memberId(MEMBER_ID)
                        .webtoonId(WEBTOON_ID)
                        .content(CONTENT)
                        .build()
        );
        DiscussionLike mockLike = likeRepository.save(
                DiscussionLike.builder()
                .userId(USER_ID)
                .discussionId(mockDiscussion.getId())
                .build()
        );
        Long discussionId = mockDiscussion.getId();
        mockDiscussion.updateLikeCount(true);
        // when
        mockMvc.perform(put("/api/v1/webtoons/discussions/" + discussionId + "/likes")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        // then
        Discussion discussion = discussionRepository.findById(discussionId).get();
        assertThat(discussion.getLikeCount()).isEqualTo(0);

        DiscussionLike like = likeRepository.findById(mockLike.getId()).get();
        assertThat(like.getFlag()).isFalse();
    }
}
