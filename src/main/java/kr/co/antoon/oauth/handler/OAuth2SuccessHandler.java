package kr.co.antoon.oauth.handler;

import kr.co.antoon.error.dto.ErrorMessage;
import kr.co.antoon.error.exception.common.NotExistsException;
import kr.co.antoon.oauth.application.JwtTokenProvider;
import kr.co.antoon.user.domain.User;
import kr.co.antoon.user.domain.vo.Role;
import kr.co.antoon.user.infrastructure.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.redis.core.RedisTemplate;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends
        SimpleUrlAuthenticationSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final RedisTemplate redisTemplate;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User)authentication.getPrincipal();
        String email = (String) oAuth2User.getAttributes().get("email");
        User user = userRepository.findByEmail(email)
                .orElseThrow(()-> new NotExistsException(ErrorMessage.NOT_EXIST_USER));

        String accessToken = jwtTokenProvider.createAccessToken(user.getId().toString(), Role.USER);
        String refreshToken = jwtTokenProvider.createRefreshToken(Long.toString(user.getId()));
        redisTemplate.opsForValue().set("RT: "+user.getId(), refreshToken,
                jwtTokenProvider.getRefreshTokenExpireTime(), TimeUnit.MILLISECONDS);

//        String targetUri = UriComponentsBuilder.fromUriString("http://localhost:3000/user/signin")
//                .build().toUriString();

        StringBuilder sb = new StringBuilder();
        sb.append("http://localhost:3000/user/signin")
                .append("?status=").append("success");
        String targetUrl = sb.toString();

        response.setContentType("application/json;charset=UTF-8");
        response.addHeader("Authorization", accessToken);
        response.addHeader("Refresh", refreshToken);
        response.setContentType("application/json;charset=UTF-8");
        response.addCookie(getCookie("accessToken", accessToken));
        response.addCookie(getCookie("refreshToken", refreshToken));
        response.sendRedirect(targetUrl);


//        log.info("targetURi : {}", targetUri);
//        getRedirectStrategy().sendRedirect(request, response, targetUri);

    }
    private Cookie getCookie(String key, String auth) {
        Cookie cookie = new Cookie(key, auth);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(7 * 24 * 60 * 60);
        cookie.setPath("/");
        cookie.setDomain("api.antoon.fun");
        return cookie;
    }
}
