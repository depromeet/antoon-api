package kr.co.antoon.discussion.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.co.antoon.user.dto.UserDetailResponse;

public record DiscussionReadResponse(
        @Schema(description = "댓글 ID")
        Long discussionId,
        @Schema(description = "댓글 내용")
        String content,
        @Schema(description = "사용자 ID")
        Long userId,
        @Schema(description = "닉네임")
        String nickname,
        @Schema(description = "이미지 URL")
        String imageUrl,
        @Schema(description = "좋아요 카운트")
        int likeCount,
        @Schema(description = "사용자 좋아요 여부")
        Boolean isUserLike
) {
        public DiscussionReadResponse(Long discussionId, String content, Long userId, UserDetailResponse user, int likeCount, Boolean isUserLike){
             this(
                  discussionId,
                  content,
                  userId,
                  user.getName(),
                  user.getImageUrl(),
                  likeCount,
                  isUserLike
             );
        }
}