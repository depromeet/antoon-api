package kr.co.antoon.oauth.presentation;

import kr.co.antoon.oauth.application.AuthService;
import kr.co.antoon.oauth.application.CustomOAuth2UserService;
import kr.co.antoon.oauth.dto.TokenResponse;
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
    private final CustomOAuth2UserService customOAuth2UserService;

    @GetMapping("/refresh")
    public TokenResponse refreshToken (HttpServletRequest request, HttpServletResponse response) {
        String token = request.getHeader("Authorization");
        return authService.refresh(token);
    }

    @RequestMapping("/")
    public String apiTest() {
        return "test!";
    }

    @PostMapping("/logout")
    public ResponseEntity<String> revoke(HttpServletRequest request) {
        try {
            String authorization = request.getHeader("Authorization");
            if (authorization != null && authorization.contains("Bearer")) {
                String tokenValue = authorization.replace("Bearer", "").trim();
                // TODO: 2022-04-30 DB에서 refreshToken 삭제하기!
                // TODO: 2022-04-30 헤더에서 토큰 삭제하기!
                //  FIXME: 어떤 에러인지

            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Invalid access token");
        }

        return ResponseEntity.ok().body("Access token invalidated successfully");
    }
}
