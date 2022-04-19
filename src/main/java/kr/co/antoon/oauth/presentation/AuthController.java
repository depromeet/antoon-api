package kr.co.antoon.oauth.presentation;

import kr.co.antoon.oauth.application.AuthService;
import kr.co.antoon.oauth.dto.TokenResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestController
@RequiredArgsConstructor
public class OauthController {
    private final AuthService authService;

    @GetMapping("/refresh")
    public TokenResponse refreshToken (HttpServletRequest request, HttpServletResponse response) {
        String token = request.getHeader("Authorization");
        return authService.refresh(token);
    }
}
