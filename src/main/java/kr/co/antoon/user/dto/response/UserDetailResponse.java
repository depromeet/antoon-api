package kr.co.antoon.user.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.co.antoon.user.domain.vo.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Builder
public class UserDetailResponse {

    @Schema(description = "이름")
    private final String name;

    @Schema(description = "이메일")
    private final String email;

    @Schema(description = "프로필 사진")
    private final String imageUrl;

    @Schema(description = "연령대")
    private final Integer age;
}
