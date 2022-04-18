package kr.co.antoon.token;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import kr.co.antoon.security.token.JwtTokenProvider_haneul;
import kr.co.antoon.user.domain.vo.Role;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.security.Key;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class JwtTokenTest {

    private String secretKey = "dafjakdjfkalujeijfakdjfajekjfladjflakdjflajdflajiejlfakdafafefqadfaeqf";

    @Spy
    private JwtTokenProvider_haneul jwtTokenProvider = new JwtTokenProvider_haneul(secretKey);

    @Test
    @DisplayName("토큰 getUserId() - 성공")
    void getUserIdSuccess() {
        String accessToken = jwtTokenProvider.createAccessToken("test", Role.USER);
        String userId = jwtTokenProvider.getUserId(accessToken);
        assertThat(userId).isEqualTo("test");
    }

    @Test
    @DisplayName("토큰 vaildate() - 성공")
    void vaildateSuccess() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        Key key = Keys.hmacShaKeyFor(keyBytes);

        String accessToken = Jwts.builder()
                .setSubject("test")
                .setExpiration(null)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        assertThat(jwtTokenProvider.validate(accessToken)).isFalse();
    }
}
