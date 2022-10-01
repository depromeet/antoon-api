package kr.co.antoon.oauth.application;

import kr.co.antoon.aws.application.AwsS3Service;
import kr.co.antoon.coin.application.AntCoinService;
import kr.co.antoon.oauth.dto.OAuth2Attribute;
import kr.co.antoon.user.domain.User;
import kr.co.antoon.user.domain.vo.Gender;
import kr.co.antoon.user.domain.vo.Role;
import kr.co.antoon.user.infrastructure.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final UserRepository userRepository;
    private final AntCoinService antCoinService;
    private final AwsS3Service awsS3Service;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        var oAuth2UserService = new DefaultOAuth2UserService();
        var oAuth2User = oAuth2UserService.loadUser(userRequest);

        var registrationId = userRequest.getClientRegistration().getRegistrationId();

        var oAuth2Attribute = OAuth2Attribute.of(registrationId, oAuth2User.getAttributes());

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(Role.USER.getKey())),
                oAuth2Attribute.getAttributes(),
                oAuth2Attribute.getAttributeKey()
        );
    }

    public void saveOrUpdate(OAuth2User oAuth2User) {
        var data = oAuth2User.getAttributes();
        var email = data.get("email").toString();

        var user = userRepository.findByEmail(email)
                .orElse(User.buildUser(
                        "",
                        email,
                        awsS3Service.randomProfileImage(),
                        Gender.NONE,
                        0));

        if (data.containsKey("profile")) {
            var profile = (HashMap<String, String>) data.get("profile");
            user.updateName(profile.get("nickname"));

            if (data.containsKey("age_range")) {
                String age = data.get("age_range").toString();
                int ageRange = Integer.parseInt(age.split("~")[0]);
                user.updateAge(ageRange);
            }

            if (data.containsKey("gender")) {
                user.updateGender(Gender.of(data.get("gender").toString()));
            }

        } else if (email.contains("gmail")) {
            user.updateName(data.get("name").toString());
        }

        userRepository.save(user);
        antCoinService.create(user.getId());
    }
}
