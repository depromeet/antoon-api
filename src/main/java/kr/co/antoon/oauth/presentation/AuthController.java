package kr.co.antoon.oauth.presentation;

import kr.co.antoon.oauth.application.AuthService;

import kr.co.antoon.oauth.application.JwtTokenProvider;
import kr.co.antoon.oauth.dto.TokenResponse;
import kr.co.antoon.user.domain.vo.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/refresh")
    public TokenResponse refreshToken (HttpServletRequest request, HttpServletResponse response) {
        String access = request.getHeader("Authorization");
        String refresh = request.getHeader("Refresh");
        return authService.refresh(refresh); //refresh 토큰만 갖고 accessToken 재발급
    }

    @PostMapping("/logout")
    public ResponseEntity logout(HttpServletRequest request) {
        String access = request.getHeader("Authorization");
        String refresh = request.getHeader("Refresh");
        authService.revokeToken(access, refresh);
        return ResponseEntity.ok().body("로그아웃 성공!");
    }
}
