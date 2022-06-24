package kr.co.antoon.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record UserDetailRequest(
        @Schema(description = "이름")
        String name,

        @Schema(description = "프로필 사진")
        String imageUrl
) { }
