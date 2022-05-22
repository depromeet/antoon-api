package kr.co.antoon.oauth.handler;

import kr.co.antoon.cache.user.UserRedisCacheService;
import kr.co.antoon.error.dto.ErrorMessage;
import kr.co.antoon.error.exception.common.NotExistsException;
import kr.co.antoon.oauth.application.JwtTokenProvider;
import kr.co.antoon.user.domain.vo.Role;
import kr.co.antoon.user.infrastructure.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final UserRedisCacheService userRedisCacheService;
    private String redirectUrl;
    private String domainUrl;

    public OAuth2SuccessHandler(
            JwtTokenProvider jwtTokenProvider,
            UserRepository userRepository,
            UserRedisCacheService userRedisCacheService,
            @Value("${url.redirect}") String redirectUrl,
            @Value("${url.domain}") String domainUrl
    ) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userRepository = userRepository;
        this.userRedisCacheService = userRedisCacheService;
        this.redirectUrl = redirectUrl;
        this.domainUrl = domainUrl;

        log.info("redirect url : {}", redirectUrl);
        log.info("domain url : {}", domainUrl);
    }

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException {
        var oAuth2User = (OAuth2User) authentication.getPrincipal();
        var email = (String) oAuth2User.getAttributes().get("email");
        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotExistsException(ErrorMessage.NOT_EXIST_USER));

        var accessToken = jwtTokenProvider.createAccessToken(user.getId().toString(), Role.USER);
        var refreshToken = jwtTokenProvider.createRefreshToken(Long.toString(user.getId()));

        userRedisCacheService.update(
                "RT: " + user.getId(),
                refreshToken,
                jwtTokenProvider.getRefreshTokenExpireTime()
        );

        var targetUrl = redirectUrl + "?status=success?access=" + accessToken + "?refresh=" + refreshToken;
        response.sendRedirect(targetUrl);
    }
}
