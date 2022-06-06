package kr.co.antoon.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record UserDetailName(
        @Schema(description = "이름")
        String name
) { }
