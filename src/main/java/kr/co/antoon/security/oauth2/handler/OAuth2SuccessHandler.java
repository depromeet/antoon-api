package kr.co.antoon.security.oauth2.handler;

import kr.co.antoon.security.oauth2.domain.UserPrincipal;
import kr.co.antoon.security.token.JwtTokenProvider;
import kr.co.antoon.user.domain.vo.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends
        SimpleUrlAuthenticationSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        String accessToken = jwtTokenProvider.createAccessToken(userPrincipal.getEmail(), Role.USER);

        String targetUri = UriComponentsBuilder.fromUriString("http://localhost:8080/home")
                .queryParam("token", accessToken)
                .build().toUriString();

        log.info(targetUri);
        // http://localhost:8080/home?token=eyJhbGciOiJIUzUxMiJ9.eyJz ~~
        // 프론트에 이렇게 넘겨줌 (리디렉션할 uri와 accessToken을 파라미터로 넘겨줌.)
        // 그리고 백에서는 http://localhost:8080/login/oauth2/code/kakao 으로 리디렉션됨.
        getRedirectStrategy().sendRedirect(request, response, targetUri);
    }
}
