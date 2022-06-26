package kr.co.antoon.character.dto.reqeust;

import javax.validation.constraints.NotBlank;

public record CharacterDiscussionRequest (
    @NotBlank(message = "content is empty")
    String content
) {}
