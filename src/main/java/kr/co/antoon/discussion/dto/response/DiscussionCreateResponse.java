package kr.co.antoon.discussion.dto.response;

public record DiscussionCreateResponse(
        Long discussionId,
        String content,
        Long userId,
        String nickname,
        String imageUrl,
        int likeCount,
        Boolean isUserLike
) {}