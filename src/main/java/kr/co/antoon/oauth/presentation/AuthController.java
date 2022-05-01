package kr.co.antoon.oauth.presentation;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.co.antoon.oauth.application.AuthService;
import kr.co.antoon.oauth.application.JwtTokenProvider;
import kr.co.antoon.oauth.dto.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Api(tags = "Auth API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;
    private final JwtTokenProvider jwtTokenProvider;

    @ApiOperation(value = "refresh token API")
    @PostMapping("/refresh")
    public TokenResponse refreshToken(HttpServletRequest request, HttpServletResponse response) {
        String access = request.getHeader("Authorization");
        String refresh = request.getHeader("Refresh");
        return authService.refresh(refresh); //refresh 토큰만 갖고 accessToken 재발급
    }

    @ApiOperation(value = "logout")
    @PostMapping("/logout")
    public ResponseEntity logout(HttpServletRequest request) {
        String access = request.getHeader("Authorization");
        String refresh = request.getHeader("Refresh");
        authService.revokeToken(access, refresh);
        return ResponseEntity.ok().body("로그아웃 성공!");
    }
}
