package kr.co.antoon.feedback.dto.request;

public record FeedbackRequest(
        String content,
        Integer score
) { }
