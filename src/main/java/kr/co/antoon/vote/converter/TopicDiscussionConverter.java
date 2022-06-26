package kr.co.antoon.vote.converter;

import kr.co.antoon.user.dto.response.UserDetailResponse;
import kr.co.antoon.vote.domain.TopicDiscussion;
import kr.co.antoon.vote.dto.response.TopicDiscussionResponse;

public class TopicDiscussionConverter {
    public static TopicDiscussionResponse toTopicCommentResponse(
            TopicDiscussion topicDiscussion,
            UserDetailResponse user,
            Boolean isUserLike,
            String time
    ) {
        return new TopicDiscussionResponse(
                topicDiscussion.getTopicId(),
                topicDiscussion,
                user,
                isUserLike,
                time
        );
    }
}
