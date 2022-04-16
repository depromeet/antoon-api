package kr.co.antoon.user.application;

import kr.co.antoon.error.dto.ErrorMessage;
import kr.co.antoon.security.token.JwtTokenProvider;
import kr.co.antoon.user.domain.User;
import kr.co.antoon.user.domain.vo.Role;
import kr.co.antoon.user.dto.response.TokenResponse;
import kr.co.antoon.user.exception.TokenExpiredException;
import kr.co.antoon.user.exception.UserNotExistException;
import kr.co.antoon.user.infrastructure.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    public TokenResponse refresh(String token) {
        String userId = jwtTokenProvider.getUserId(token);
        User user = userRepository.findByEmail(userId)
                .orElseThrow(()-> new UserNotExistException(ErrorMessage.NOT_EXIST_USER));

        String refreshToken = user.getRefreshToken();
        if (!jwtTokenProvider.validate(refreshToken)) {
            throw new TokenExpiredException(ErrorMessage.EXPIRED_TOKEN);
        }
        String accessToken = jwtTokenProvider.createAccessToken(user.getEmail(), Role.USER);

        return new TokenResponse(accessToken);
    }
}
