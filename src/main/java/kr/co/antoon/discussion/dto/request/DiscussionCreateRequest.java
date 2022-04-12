package kr.co.antoon.discussion.dto.request;

import javax.validation.constraints.NotBlank;

public record DiscussionCreateRequest(
        @NotBlank(message = "content is empty")
        String content
) {}