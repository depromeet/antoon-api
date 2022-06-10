package kr.co.antoon.feedback.dto;

public record FeedbackRequest(
        String content,
        Integer score
) { }