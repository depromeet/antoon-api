package kr.co.antoon.user;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import kr.co.antoon.error.exception.common.NotExistsException;
import kr.co.antoon.oauth.application.AuthService;
import kr.co.antoon.oauth.application.JwtTokenProvider;
import kr.co.antoon.user.domain.User;
import kr.co.antoon.user.domain.vo.Role;
import kr.co.antoon.oauth.dto.TokenResponse;
import kr.co.antoon.oauth.exception.TokenExpiredException;
import kr.co.antoon.user.infrastructure.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.security.Key;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class RefreshTest {

    private String secretKey = "dafjakdjfkalujeijfakdjfajekjfladjflakdjflajdflajiejlfakdafafefqadfaeqf";

    @Spy
    private JwtTokenProvider jwtTokenProvider = new JwtTokenProvider(secretKey);

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthService authService;

    @Test
    @DisplayName("토큰 재발급 - 성공")
    void refreshSuccess() {
        //given
        String accessToken = jwtTokenProvider.createAccessToken("test", Role.USER);
        String refreshToken = jwtTokenProvider.createRefreshToken("test");

        given(
                userRepository.findByEmail("test")
        ).willReturn(
                Optional.ofNullable(User.builder()
                        .name("name")
                        .email("test")
                        .imageUrl("imageUrl")
                        .role(Role.USER)
                        .build()
                )
        );

        // when
        TokenResponse tokenResponse = authService.refresh(refreshToken);

        // then
        assertThat(tokenResponse.accessToken()).isNotNull();
    }

    @Test
    @DisplayName("토큰 재발급 - 실패(존재하지 않는 사용자)")
    void refreshFailBecauseNotExistUser() {
        // given
        String accessToken = jwtTokenProvider.createAccessToken("test", Role.USER);
        String refreshToken = jwtTokenProvider.createRefreshToken("test");
        given(
                userRepository.findByEmail("test")
        ).willReturn(Optional.empty());

        // when & then
        Assertions.assertThrows(NotExistsException.class, () -> {
            authService.refresh(refreshToken);
        });
    }

    @Test
    @DisplayName("토큰 재발급 - 실패(만료된 refreshToken)")
    void refreshFailBecauseExpiredToken() {
        // given
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        Key key = Keys.hmacShaKeyFor(keyBytes);

        String accessToken = jwtTokenProvider.createAccessToken("test", Role.USER);
        String refreshToken = Jwts.builder()
                .setSubject("test")
                .setExpiration(null)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        given(
                userRepository.findByEmail("test")
        ).willReturn(
                Optional.ofNullable(User.builder()
                        .name("name")
                        .email("test")
                        .imageUrl("imageUrl")
                        .role(Role.USER)
                        .build()
                )
        );

        // when & then
        Assertions.assertThrows(TokenExpiredException.class, () -> {
            authService.refresh(refreshToken);
        });
    }
}
