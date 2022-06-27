package kr.co.antoon.vote.dto.request;

import javax.validation.constraints.NotBlank;

public record TopicDiscussionCreateRequest(
    @NotBlank(message = "content is empty")
    String content
) {}
