package kr.co.antoon.oauth.presentation;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.co.antoon.common.dto.ResponseDto;
import kr.co.antoon.oauth.application.AuthService;
import kr.co.antoon.oauth.application.JwtTokenProvider;
import kr.co.antoon.oauth.dto.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Api(tags = "Auth API")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/auth", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthController {
    private final AuthService authService;

    @ApiOperation(value = "refresh token API")
    @PostMapping("/refresh")
    public ResponseEntity<TokenResponse> refreshToken(HttpServletRequest request, HttpServletResponse response) {
        String access = request.getHeader("Authorization");
        String refresh = request.getHeader("Refresh");
        return ResponseDto.ok(authService.refresh(refresh));
    }

    @ApiOperation(value = "logout")
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request) {
        String access = request.getHeader("Authorization");
        String refresh = request.getHeader("Refresh");
        authService.revokeToken(access, refresh);
        return ResponseDto.noContent();
    }
}
