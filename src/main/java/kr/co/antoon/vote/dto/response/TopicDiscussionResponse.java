package kr.co.antoon.vote.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.co.antoon.user.dto.response.UserDetailResponse;
import kr.co.antoon.vote.domain.TopicDiscussion;

public record TopicDiscussionResponse(
        @Schema(description = "토픽 ID")
        Long topicId,
        @Schema(description = "댓글 ID")
        Long commentId,
        @Schema(description = "댓글 내용")
        String content,
        @Schema(description = "사용자 ID")
        Long userId,
        @Schema(description = "닉네임")
        String nickname,
        @Schema(description = "작성자 이미지 URL")
        String imageUrl,
        @Schema(description = "좋아요 카운트")
        int likeCount,
        @Schema(description = "사용자 좋아요 여부")
        Boolean isUserLike,
        @Schema(description = "작성 시간")
        String time
) {
    public TopicDiscussionResponse(Long topicId, TopicDiscussion comment, UserDetailResponse user, Boolean isUserLike, String time) {
        this(
                topicId,
                comment.getId(),
                comment.getContent(),
                comment.getUserId(),
                user.name(),
                user.imageUrl(),
                comment.getLikeCount(),
                isUserLike,
                time
        );
    }
}
