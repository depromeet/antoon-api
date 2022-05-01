package kr.co.antoon.discussion.dto.response;

public record DiscussionReadResponse(
        Long discussionId,
        String content,
        Long memberId,
        int likeCount,
        Boolean isUserLike
) {}