package kr.co.antoon.oauth.presentation;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.co.antoon.common.dto.ResponseDto;
import kr.co.antoon.common.dto.SwaggerNote;
import kr.co.antoon.oauth.application.AuthService;
import kr.co.antoon.oauth.dto.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static kr.co.antoon.common.util.CommonUtil.APPLICATION_JSON_UTF_8;

@Api(tags = "Auth API")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/auth", produces = APPLICATION_JSON_UTF_8)
public class AuthController {
    private final AuthService authService;

    @ApiOperation(value = "refresh token API", notes = SwaggerNote.AUTH_RFRESH)
    @PostMapping("/refresh")
    public ResponseEntity<TokenResponse> refreshToken(
            @RequestHeader(value = "Refresh") String refreshToken
    ) {
        var response = authService.refresh(refreshToken);
        return ResponseDto.ok(response);
    }

    @ApiOperation(value = "logout", notes = SwaggerNote.AUTH_LOGOUT)
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(
            @RequestHeader(value = "Authorization") String access,
            @RequestHeader(value = "Refresh") String refreshToken
    ) {
        authService.revokeToken(access, refreshToken);
        return ResponseDto.noContent();
    }
}
