package kr.co.antoon.oauth.handler;

import kr.co.antoon.cache.user.UserRedisCacheService;
import kr.co.antoon.oauth.application.CustomOAuth2UserService;
import kr.co.antoon.oauth.application.JwtTokenProvider;
import kr.co.antoon.user.application.UserService;
import kr.co.antoon.user.domain.vo.Role;
import kr.co.antoon.user.domain.vo.Status;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRedisCacheService userRedisCacheService;
    private final String redirectUrl;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final UserService userService;

    public OAuth2SuccessHandler(
            JwtTokenProvider jwtTokenProvider,
            UserRedisCacheService userRedisCacheService,
            @Value("${url.redirect}") String redirectUrl,
            CustomOAuth2UserService customOAuth2UserService,
            UserService userService
    ) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userRedisCacheService = userRedisCacheService;
        this.redirectUrl = redirectUrl;
        this.customOAuth2UserService = customOAuth2UserService;
        this.userService = userService;
    }

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException {
        var oAuth2User = (OAuth2User) authentication.getPrincipal();
        var email = (String) oAuth2User.getAttributes().get("email");

        var checkUserExist = userService.checkExistEmail(email);
        var status = Status.of(checkUserExist);

        customOAuth2UserService.saveOrUpdate(oAuth2User);

        var user = userService.findByEmail(email);
        var accessToken = jwtTokenProvider.createAccessToken(user.getId().toString(), Role.USER.getKey());
        var refreshToken = jwtTokenProvider.createRefreshToken(Long.toString(user.getId()));

        userRedisCacheService.update(
                user.getId().toString(),
                refreshToken,
                jwtTokenProvider.getRefreshTokenExpireTime()
        );

        var targetUrl = redirectUrl + "?status=" + status + "?access=" + accessToken + "?refresh=" + refreshToken;
        response.sendRedirect(targetUrl);
    }
}
