package kr.co.antoon.discussion.dto.response;

public record DiscussionUpdateResponse(
        Long discussionId,
        String content,
        Long memberId,
        int likeCount,
        Boolean isUserLike
) { }