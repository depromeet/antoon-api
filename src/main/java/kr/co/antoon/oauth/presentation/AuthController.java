package kr.co.antoon.oauth.presentation;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.co.antoon.common.dto.ResponseDto;
import kr.co.antoon.common.dto.SwaggerNote;
import kr.co.antoon.oauth.application.AuthService;
import kr.co.antoon.oauth.config.AuthUser;
import kr.co.antoon.oauth.dto.AuthInfo;
import kr.co.antoon.oauth.dto.TokenResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Api(tags = "Auth API")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/auth")
public class AuthController {
    private final static int ACCESS_TOKEN_LENGTH = 7;
    private final AuthService authService;

    @ApiOperation(value = "refresh token API", notes = SwaggerNote.AUTH_RFRESH)
    @PostMapping("/refresh")
    public ResponseEntity<TokenResponse> refreshToken(
            @RequestHeader(value = "Refresh") String refreshToken
    ) {
        return ResponseDto.ok(authService.refresh(refreshToken));
    }

    @ApiOperation(value = "logout", notes = SwaggerNote.AUTH_LOGOUT)
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(
            @RequestHeader(value = "Authorization") String access,
            @RequestHeader(value = "Refresh") String refreshToken
    ) {
        String accessToken = access.substring(ACCESS_TOKEN_LENGTH);
        authService.revokeToken(accessToken, refreshToken);
        return ResponseDto.noContent();
    }

    @GetMapping("/test")
    public AuthInfo test(@AuthUser AuthInfo info) {
        return info;
    }
}
