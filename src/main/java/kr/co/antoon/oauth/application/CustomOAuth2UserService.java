package kr.co.antoon.oauth.application;

import kr.co.antoon.oauth.dto.OAuth2Attribute;
import kr.co.antoon.user.domain.User;
import kr.co.antoon.user.domain.vo.Gender;
import kr.co.antoon.user.infrastructure.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.concurrent.TimeUnit;


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

        saveOrUpdate(oAuth2Attribute);

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
                oAuth2Attribute.getAttributes(),
                oAuth2Attribute.getAttributeKey()
        );
    }

    private void saveOrUpdate(OAuth2Attribute attributes) {
        User user = userRepository.findByEmail(attributes.getEmail())
                .map(entity -> entity.update(attributes.getName(),
                        attributes.getImageUrl()))
                .orElse(attributes.toEntity());

        user.updateAge(0);
        user.updateGender(Gender.NONE);

        log.info("attributes : {}", attributes);

        String age = attributes.getAgeRange();
        if(age!=null) {
            int ageRange = Integer.parseInt(age.split("~")[0]);
            user.updateAge(ageRange);
        }

        String gender = attributes.getGender();
        if(gender!=null) {
            switch (gender) {
                case "female":
                    user.updateGender(Gender.FEMALE);
                    break;
                case "male":
                    user.updateGender(Gender.MALE);
                    break;
                default:
                    user.updateGender(Gender.NONE);
            }
        }

        userRepository.save(user);
    }
}
