package kr.co.antoon.oauth.handler;

import kr.co.antoon.oauth.application.JwtTokenProvider;
import kr.co.antoon.user.domain.vo.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
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
        OAuth2User oAuth2User = (OAuth2User)authentication.getPrincipal();
        String email = (String) oAuth2User.getAttributes().get("email");
        String accessToken = jwtTokenProvider.createAccessToken(email, Role.USER);

        response.setContentType("text/html;charset=UTF-8");
        response.addHeader("Authorization", accessToken);
        response.setContentType("application/json;charset=UTF-8");

        var writer = response.getWriter();
        writer.println(accessToken);
        writer.flush();
    }
}
