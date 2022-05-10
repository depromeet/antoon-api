package kr.co.antoon.oauth.presentation;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.co.antoon.common.dto.ResponseDto;
import kr.co.antoon.common.dto.SwaggerNote;
import kr.co.antoon.oauth.application.AuthService;
import kr.co.antoon.oauth.config.AuthUser;
import kr.co.antoon.oauth.dto.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Api(tags = "Auth API")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/auth")
public class AuthController {
    private final AuthService authService;

    @ApiOperation(value = "refresh token API", notes = SwaggerNote.AUTH_RFRESH)
    @PostMapping("/refresh")
    public ResponseEntity<TokenResponse> refreshToken(HttpServletRequest request, HttpServletResponse response) {
        String refresh = request.getHeader("Refresh");
        return ResponseDto.ok(authService.refresh(refresh));
    }

    @ApiOperation(value = "logout", notes = SwaggerNote.AUTH_LOGOUT)
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request) {
        String access = request.getHeader("Authorization").substring(7);
        String refresh = request.getHeader("Refresh");
        authService.revokeToken(access, refresh);
        return ResponseDto.noContent();
    }

    @GetMapping("/test")
    public Long test(@AuthUser Long userId) {
        return userId;
    }
}
