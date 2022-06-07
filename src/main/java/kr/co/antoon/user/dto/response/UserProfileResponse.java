package kr.co.antoon.user.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record UserProfileResponse(
        @Schema(description = "기본 프로필 이미지")
        String defaultProfileImageUrl
) { }
