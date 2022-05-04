package kr.co.antoon.user.converter;

import kr.co.antoon.user.domain.User;
import kr.co.antoon.user.dto.response.UserDetailResponse;

public class UserConverter {
    public static UserDetailResponse toUserDetailDto(User user) {
        return UserDetailResponse.builder()
                .name(user.getName())
                .email(user.getEmail())
                .imageUrl(user.getImageUrl())
                .build();
    }
}
