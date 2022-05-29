package kr.co.antoon.oauth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.co.antoon.user.domain.vo.Role;

import java.util.List;

public record AuthInfo(
        @Schema(description = "사용자 ID")
        Long userId,
        @Schema(description = "사용자 권한")
        List<Role> roles
) { }