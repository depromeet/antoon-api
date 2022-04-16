package kr.co.antoon.oauth.presentation;

import kr.co.antoon.oauth.application.TokenService;
import kr.co.antoon.oauth.dto.TokenDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestController
@RequiredArgsConstructor
public class OauthController {
    private final TokenService tokenService;

    @GetMapping("/token/expired")
    public String auth() {
        return "refresh token!";
    }

    @GetMapping("/token/refresh")
    public String refreshAuth(HttpServletRequest request, HttpServletResponse response) {
        log.info("request : ", request);

        String token = request.getHeader("Refresh");
        log.info("Header Refresh : ", token);

        if (token != null && tokenService.verifyToken(token)) {
            String email = tokenService.getUid(token);
            TokenDto newToken = tokenService.generateToken(email, "USER");

            response.addHeader("AccessToken", newToken.getToken());
            response.addHeader("Refresh", newToken.getRefreshToken());
            response.setContentType("application/json;charset=UTF-8");

            return "success";
        }

        throw new RuntimeException();
    }

}
