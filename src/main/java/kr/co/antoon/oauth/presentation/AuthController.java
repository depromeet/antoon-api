package kr.co.antoon.oauth.presentation;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.co.antoon.common.dto.ResponseDto;
import kr.co.antoon.common.dto.SwaggerNote;
import kr.co.antoon.oauth.application.AuthService;
import kr.co.antoon.oauth.config.AuthUser;
import kr.co.antoon.oauth.dto.TokenResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Api(tags = "Auth API")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/auth")
public class AuthController {
    private final AuthService authService;

    @ApiOperation(value = "refresh token API", notes = SwaggerNote.AUTH_RFRESH)
    @PostMapping("/refresh")
    public ResponseEntity<TokenResponse> refreshToken(@RequestHeader(value = "Refresh") String refreshToken) {
        return ResponseDto.ok(authService.refresh(refreshToken));
    }

    @ApiOperation(value = "logout", notes = SwaggerNote.AUTH_LOGOUT)
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestHeader(value = "Authorization") String access,
                                       @RequestHeader(value = "Refresh") String refreshToken) {
        String accessToken = access.substring(7);
        authService.revokeToken(accessToken, refreshToken);
        return ResponseDto.noContent();
    }

    @GetMapping("/test")
    public Long test(@AuthUser Long userId) {
        return userId;
    }
}
