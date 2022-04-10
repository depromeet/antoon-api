package kr.co.toonzip.security.oauth2;

import kr.co.toonzip.security.token.JwtTokenProvider;
import kr.co.toonzip.security.oauth2.domain.UserPrincipal;
import kr.co.toonzip.user.domain.RefreshToken;
import kr.co.toonzip.user.infrastructure.RefreshTokenRepository;
import kr.co.toonzip.util.CookieUtil;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.Optional;

import static org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames.REFRESH_TOKEN;


@Component
public class OAuth2AuthenticationSuccessHandler extends
        SavedRequestAwareAuthenticationSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;
    private final HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    OAuth2AuthenticationSuccessHandler(@Lazy JwtTokenProvider jwtTokenProvider, @Lazy RefreshTokenRepository refreshTokenRepository,
                                       @Lazy HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.refreshTokenRepository = refreshTokenRepository;
        this.httpCookieOAuth2AuthorizationRequestRepository =httpCookieOAuth2AuthorizationRequestRepository;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String targetUrl = determineTargetUrl(request, response, authentication);
        if (response.isCommitted()) {
            logger.debug("응답이 이미 커밋되었습니다. " + targetUrl + "로 리다이렉션을 할 수 없습니다");
            return;
        }
        clearAuthenticationAttributes(request, response);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    @Override
    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        Optional<String> redirectUri = CookieUtil.getCookie(
                        request,
                        HttpCookieOAuth2AuthorizationRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME
                     ).map(Cookie::getValue);

        if (redirectUri.isPresent() && !isAuthorizedRedirectUri(redirectUri.get())) {
            throw new IllegalArgumentException("승인되지 않은 redirect uri가 있어 인증을 진행할 수 없습니다.");
        }

        String targetUrl = redirectUri.orElse(getDefaultTargetUrl());
//        OAuth2AuthenticationToken authToken = (OAuth2AuthenticationToken) authentication;
//        String registrationId = authToken.getAuthorizedClientRegistrationId();
//
        UserPrincipal userPrincipal = ((UserPrincipal) authentication.getPrincipal());
//        OAuth2UserInfo userInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(registrationId, userPrincipal.getAttributes());
//        Collection<? extends GrantedAuthority> authorities = ((UserPrincipal) authentication.getPrincipal()).getAuthorities();

        String accessToken = jwtTokenProvider.createAccessToken(authentication);
        String refreshToken = jwtTokenProvider.createRefreshToken(authentication);

        RefreshToken userRefreshToken = refreshTokenRepository.findByUserId(userPrincipal.getId());
        if (userRefreshToken != null) {
            userRefreshToken.update(refreshToken);
        } else {
            userRefreshToken = new RefreshToken(userPrincipal.getId(), refreshToken);
            refreshTokenRepository.saveAndFlush(userRefreshToken);
        }

        int cookieMaxAge = (int) ((60 * 60 * 1000L) * 24);

        CookieUtil.deleteCookie(request, response, REFRESH_TOKEN);
        CookieUtil.addCookie(response, REFRESH_TOKEN, refreshToken, cookieMaxAge);

        return UriComponentsBuilder.fromUriString(targetUrl)
                .queryParam("token", accessToken)
                .build().toUriString();
    }

    protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
        httpCookieOAuth2AuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
    }

    private boolean isAuthorizedRedirectUri(String uri) {
        URI clientRedirectUrl = URI.create(uri);
        URI authorizedURI = URI.create("http://localhost:3000/oauth2/redirect");

        return authorizedURI.getHost().equalsIgnoreCase(clientRedirectUrl.getHost())
                && authorizedURI.getPort() == clientRedirectUrl.getPort();
    }
}
