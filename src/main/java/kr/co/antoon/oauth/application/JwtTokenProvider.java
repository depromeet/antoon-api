package kr.co.antoon.oauth.application;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import kr.co.antoon.user.domain.vo.Role;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class JwtTokenProvider {
    private Key secretKey;
    private static final String BEARER_TYPE = "bearer";
    private static final long ACCESS_TOKEN_EXPIRE_TIME = ((60 * 60 * 1000L) * 24) * 60;  // 60일
    private static final long REFRESH_TOKEN_EXPIRE_TIME = ((60 * 60 * 1000L) * 24) * 60;  // 60일

    public JwtTokenProvider(@Value("${jwt.secret}") String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);

        log.info("key : {}", secretKey);
    }

    public String createAccessToken(String userId, Role role) {
        Date now = new Date();
        return Jwts.builder()
                .setSubject(userId)
                .claim("role", role)
                .setExpiration(new Date(now.getTime() + ACCESS_TOKEN_EXPIRE_TIME))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public String createRefreshToken(String userId) {
        Date now = new Date();
        return Jwts.builder()
                .setSubject(userId)
                .setExpiration(new Date(now.getTime() + REFRESH_TOKEN_EXPIRE_TIME))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validate(String token) {
        try {
            return getClaims(token)
                    .getExpiration()
                    .after(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    public Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Long getUserId(String token) {
        return Long.parseLong(getClaims(token).getSubject());
    }

    public Authentication getAuthentication(String token) {
        return new UsernamePasswordAuthenticationToken(
                getUserId(token), null, getAuthorities());
    }

    public Collection<GrantedAuthority> getAuthorities() {
        return Collections.singletonList(
                new SimpleGrantedAuthority("ROLE_USER")
        );
    }

    public long getRefreshTokenExpireTime() {
        return REFRESH_TOKEN_EXPIRE_TIME;
    }

    public Long getExpiration(String accessToken) {
        // accessToken 남은 유효시간
        Date expiration = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(accessToken)
                .getBody().getExpiration();
        // 현재 시간
        long now = new Date().getTime();
        return (expiration.getTime() - now);
    }
}
