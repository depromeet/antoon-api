package kr.co.antoon.feedback.dto;

import kr.co.antoon.feedback.domain.Feedback;
import kr.co.antoon.feedback.domain.vo.Score;
import kr.co.antoon.feedback.domain.vo.Status;
import kr.co.antoon.user.dto.response.UserDetailResponse;

import java.time.LocalDateTime;

public record FeedbackResponse(
        Long id,
        String content,
        Score score,
        Status status,
        Long userId,
        String name,
        String email,
        Integer age,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt
) {
    public FeedbackResponse(Feedback feedback, UserDetailResponse user) {
        this(
                feedback.getId(),
                feedback.getContent(),
                feedback.getScore(),
                feedback.getStatus(),
                feedback.getUserId(),
                user.name(),
                user.email(),
                user.age(),
                feedback.getCreatedAt(),
                feedback.getModifiedAt()
        );
    }
}