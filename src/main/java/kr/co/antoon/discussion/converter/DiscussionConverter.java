package kr.co.antoon.discussion.converter;

import kr.co.antoon.discussion.domain.Discussion;
import kr.co.antoon.discussion.dto.response.DiscussionResponse;
import kr.co.antoon.user.dto.response.UserDetailResponse;

public class DiscussionConverter {
    public static DiscussionResponse toDiscussionResponse(
            Discussion discussion,
            UserDetailResponse user,
            Boolean isUserLike
    ) {
        return new DiscussionResponse(
                discussion.getWebtoonId(),
                discussion,
                user,
                isUserLike
        );
    }
}