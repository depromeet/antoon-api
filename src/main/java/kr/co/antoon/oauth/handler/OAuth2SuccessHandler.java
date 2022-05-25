package kr.co.antoon.oauth.handler;

import kr.co.antoon.cache.user.UserRedisCacheService;
import kr.co.antoon.error.dto.ErrorMessage;
import kr.co.antoon.error.exception.common.NotExistsException;
import kr.co.antoon.oauth.application.JwtTokenProvider;
import kr.co.antoon.oauth.dto.OAuth2Attribute;
import kr.co.antoon.user.domain.User;
import kr.co.antoon.user.domain.vo.Gender;
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
import java.util.HashMap;

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
        String status = "success";
        log.info("handler attributes : {}", oAuth2User.getAttributes().get("profile"));
        var test = oAuth2User.getAttributes().get("profile");
        log.info("type: {}", test.getClass());

        Boolean isSignIn = userRepository.existsByEmail(email);
        if(!isSignIn) {
            status = "signup";
        }

        saveOrUpdate(oAuth2User);

        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotExistsException(ErrorMessage.NOT_EXIST_USER));

        var accessToken = jwtTokenProvider.createAccessToken(user.getId().toString(), Role.USER.getKey());
        var refreshToken = jwtTokenProvider.createRefreshToken(Long.toString(user.getId()));

        userRedisCacheService.update(
                "RT: " + user.getId(),
                refreshToken,
                jwtTokenProvider.getRefreshTokenExpireTime()
        );

        var targetUrl = redirectUrl + "?status="+ status + "?access=" + accessToken + "?refresh=" + refreshToken;
        response.sendRedirect(targetUrl);
    }

    private void saveOrUpdate(OAuth2User oAuth2User) {
        var data = oAuth2User.getAttributes();
        HashMap<String, String> profile = (HashMap<String, String>) data.get("profile");

        User user = userRepository.findByEmail(data.get("email").toString())
                .map(entity -> entity.update(
                        profile.get("nickname"),
                        profile.get("profile_image_url")
                ))
                .orElse(User.builder()
                        .name(profile.get("nickname"))
                        .email(data.get("email").toString())
                        .imageUrl(profile.get("https://antoon-api-bucket.s3.ap-northeast-2.amazonaws.com/color%3Dyellow.png"))
                        .gender(Gender.NONE)
                        .role(Role.USER)
                        .age(0)
                        .build());

        String profileImg = profile.get("profile_image_url");
        if(profileImg != null) {
            user.updateImageUrl(profileImg);
        }

        String age = data.get("age_range").toString();
        if (age != null) {
            int ageRange = Integer.parseInt(age.split("~")[0]);
            user.updateAge(ageRange);
        }

        String gender = data.get("gender").toString();
        if (gender != null) {
            switch (gender) {
                case "female" -> user.updateGender(Gender.FEMALE);
                case "male" -> user.updateGender(Gender.MALE);
                default -> user.updateGender(Gender.NONE);
            }
        }

        userRepository.save(user);
    }
}