package kr.co.antoon.oauth.dto;

import kr.co.antoon.user.domain.vo.Role;

import java.util.List;

public record AuthInfo (
        Long userId,
        List<Role> roles
) {}
