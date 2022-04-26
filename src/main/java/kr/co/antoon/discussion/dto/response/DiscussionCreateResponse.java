package kr.co.antoon.discussion.dto.response;

public record DiscussionCreateResponse(
        Long discussionId,
        String content,
        Long memberId,
        int likeCount,
        Boolean isUserLike
) { }