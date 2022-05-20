package kr.co.antoon.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.co.antoon.user.domain.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserDetailRequest {
    @Schema(description = "이름")
    private final String name;

    @Schema(description = "프로필 사진")
    private final String imageUrl;
}
