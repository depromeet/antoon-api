package kr.co.antoon.user.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.co.antoon.user.domain.User;

public record UserDetailResponse(
        @Schema(description = "사용자 인덱스")
        Long id,

        @Schema(description = "이름")
        String name,

        @Schema(description = "이메일")
        String email,

        @Schema(description = "프로필 사진")
        String imageUrl,

        @Schema(description = "연령대")
        Integer age
) {
    public UserDetailResponse(User user) {
        this(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getImageUrl(),
                user.getAge()
        );
    }
}