package kr.co.antoon.security.oauth2.handler;

import kr.co.antoon.security.oauth2.domain.UserPrincipal;
import kr.co.antoon.security.token.JwtTokenProvider_haneul;
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
public class OAuth2SuccessHandler_haneul extends
        SimpleUrlAuthenticationSuccessHandler {

    private final JwtTokenProvider_haneul jwtTokenProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        String accessToken = jwtTokenProvider.createAccessToken(userPrincipal.getEmail(), Role.USER);

        String targetUri = UriComponentsBuilder.fromUriString("http://localhost:8080/oauth2/redirect")
                .queryParam("token", accessToken)
                .build().toUriString();

        log.info("targetURi : {}", targetUri);
        getRedirectStrategy().sendRedirect(request, response, targetUri);
    }
}
