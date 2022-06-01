package kr.co.antoon.oauth.application;

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

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = oAuth2UserService.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        OAuth2Attribute oAuth2Attribute =
                OAuth2Attribute.of(registrationId, oAuth2User.getAttributes());

        log.info("OAuth2Attribute : {}", oAuth2Attribute);

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(Role.USER.getKey())),
                oAuth2Attribute.getAttributes(),
                oAuth2Attribute.getAttributeKey()
        );
    }

    public Boolean checkExistEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public void saveOrUpdate(OAuth2User oAuth2User) {
        var data = oAuth2User.getAttributes();
        var profile = (HashMap<String, String>) data.get("profile");

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

        var profileImg = profile.get("profile_image_url");
        if (profileImg != null) {
            user.updateImageUrl(profileImg);
        }

        var age = data.get("age_range").toString();
        if (age != null) {
            int ageRange = Integer.parseInt(age.split("~")[0]);
            user.updateAge(ageRange);
        }

        var gender = Gender.of(data.get("gender").toString());
        user.updateGender(gender);

        userRepository.save(user);
    }
}
