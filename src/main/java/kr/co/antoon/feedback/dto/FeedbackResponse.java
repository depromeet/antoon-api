package kr.co.antoon.feedback.dto;

import kr.co.antoon.feedback.entity.Feedback;
import kr.co.antoon.feedback.entity.vo.Score;
import kr.co.antoon.feedback.entity.vo.Status;

import java.time.LocalDateTime;

public record FeedbackResponse(
        Long id,
        String content,
        Score score,
        Status status,
        Long userId,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt
) {
    public FeedbackResponse(Feedback feedback) {
        this(
                feedback.getId(),
                feedback.getContent(),
                feedback.getScore(),
                feedback.getStatus(),
                feedback.getUserId(),
                feedback.getCreatedAt(),
                feedback.getModifiedAt()
        );
    }
}
