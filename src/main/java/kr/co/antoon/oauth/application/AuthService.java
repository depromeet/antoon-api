package kr.co.antoon.oauth.application;

import kr.co.antoon.cache.user.UserRedisCacheService;
import kr.co.antoon.error.exception.oauth.TokenExpiredException;
import kr.co.antoon.error.exception.oauth.TokenInvalidException;
import kr.co.antoon.error.exception.user.NotExistsUserException;
import kr.co.antoon.oauth.dto.TokenResponse;
import kr.co.antoon.user.domain.vo.Role;
import kr.co.antoon.user.infrastructure.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final UserRedisCacheService userRedisCacheService;


    @Transactional
    public TokenResponse refresh(String refreshToken) {
        var userId = jwtTokenProvider.getUserId(refreshToken);
        var user = userRepository.findById(userId)
                .orElseThrow(NotExistsUserException::new);

        //redis refreshToken
        var redisRT = userRedisCacheService.get(userId);

        // (추가) 로그아웃되어 Redis 에 RefreshToken 이 존재하지 않는 경우 처리
        if (ObjectUtils.isEmpty(refreshToken)) {
            throw new TokenExpiredException();
        }
        //redisRT와 받은 refreshToken이 동일한지 유효성 검사 -> 헤더에서 rt 가져와
        if (!jwtTokenProvider.validate(redisRT)) { //만료 검사
            throw new TokenExpiredException();
        } else if (!redisRT.equals(refreshToken)) { //유효성 검사(사용자 일치 여부 판단)
            throw new TokenInvalidException();
        }

        var newAccessToken = jwtTokenProvider.createAccessToken(String.valueOf(user.getId()), Role.USER.getKey());
        var newRefreshToken = jwtTokenProvider.createRefreshToken(String.valueOf(user.getId()));

        //redis refreshToken 갱신
        userRedisCacheService.update(
                "RT: " + user.getId(),
                newRefreshToken,
                jwtTokenProvider.getRefreshTokenExpireTime()
        );

        return new TokenResponse(newAccessToken, newRefreshToken);
    }

    @Transactional
    public void revokeToken(String accessToken) {
        var access = accessToken.substring(7);

        if (!jwtTokenProvider.validate(access)) { //유효성 검사
            throw new TokenInvalidException();
        }

        Long userId = jwtTokenProvider.getUserId(access);
        if (!userRepository.existsById(userId)) {
            throw new NotExistsUserException();
        }

        if (userRedisCacheService.get(userId) != null) {
            userRedisCacheService.delete(userId);
        }
        var expiration = jwtTokenProvider.getExpiration(access);
        userRedisCacheService.update(access, "logout", expiration);
    }
}
