package kr.co.antoon.oauth.handler;

import kr.co.antoon.error.dto.ErrorMessage;
import kr.co.antoon.error.exception.common.NotExistsException;
import kr.co.antoon.oauth.application.JwtTokenProvider;
import kr.co.antoon.user.domain.User;
import kr.co.antoon.user.domain.vo.Role;
import kr.co.antoon.user.infrastructure.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final RedisTemplate redisTemplate;
    private String redirectUrl;
    private String domainUrl;

    public OAuth2SuccessHandler(
            JwtTokenProvider jwtTokenProvider,
            UserRepository userRepository,
            RedisTemplate redisTemplate,
            @Value("${url.redirect}") String redirectUrl,
            @Value("${url.domain}") String domainUrl
    ) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userRepository = userRepository;
        this.redisTemplate = redisTemplate;
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
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = (String) oAuth2User.getAttributes().get("email");
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotExistsException(ErrorMessage.NOT_EXIST_USER));

        String accessToken = jwtTokenProvider.createAccessToken(user.getId().toString(), Role.USER.getKey());
        String refreshToken = jwtTokenProvider.createRefreshToken(Long.toString(user.getId()));
        redisTemplate.opsForValue().set("RT: " + user.getId(), refreshToken,
                jwtTokenProvider.getRefreshTokenExpireTime(), TimeUnit.MILLISECONDS);


        String targetUrl = redirectUrl + "?status=" + "success";

        response.setContentType("application/json;charset=UTF-8");
        response.addHeader("Authorization", accessToken);
        response.addHeader("Refresh", refreshToken);

        response.addCookie(getCookie("accessToken", accessToken));
        response.addCookie(getCookie("refreshToken", refreshToken));

        response.sendRedirect(targetUrl);

    }

    private Cookie getCookie(String key, String auth) {
        Cookie cookie = new Cookie(key, auth);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setMaxAge(7 * 24 * 60 * 60);
        cookie.setPath("/");
        cookie.setDomain(domainUrl);
        return cookie;
    }
}
