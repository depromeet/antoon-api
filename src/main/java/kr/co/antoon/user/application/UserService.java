package kr.co.antoon.user.application;

import kr.co.antoon.error.dto.ErrorMessage;
import kr.co.antoon.error.exception.common.NotExistsException;
import kr.co.antoon.user.domain.User;
import kr.co.antoon.user.dto.response.UserDetailResponse;
import kr.co.antoon.user.infrastructure.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public UserDetailResponse findById(Long id) {
        User user =  userRepository.findById(id)
                .orElseThrow(() -> new NotExistsException(ErrorMessage.NOT_EXIST_USER));

        return UserDetailResponse.builder()
                .name(user.getName())
                .email(user.getEmail())
                .imageUrl(user.getImageUrl())
                .age(user.getAge())
                .build();
    }
}
    public long count() {
        return userRepository.count();
    }
}
