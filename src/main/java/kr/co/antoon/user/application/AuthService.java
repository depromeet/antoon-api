package kr.co.antoon.user.application;

import kr.co.antoon.security.token.JwtTokenProvider;
import kr.co.antoon.user.domain.User;
import kr.co.antoon.user.domain.vo.Role;
import kr.co.antoon.user.dto.response.TokenResponse;
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

    // exception 수정 필요
    public TokenResponse refresh(String token) throws Exception {
        String userId = jwtTokenProvider.getUserId(token);
        User user = userRepository.findByEmail(userId)
                .orElseThrow(Exception::new);

        String refreshToken = user.getRefreshToken();
        if (jwtTokenProvider.validate(refreshToken)) {
            throw new Exception();
        }

        String newAccessToken = jwtTokenProvider.createAccessToken(user.getEmail(), Role.USER);

        return new TokenResponse(newAccessToken);
    }
}
