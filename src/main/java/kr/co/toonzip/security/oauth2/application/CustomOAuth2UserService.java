package kr.co.toonzip.security.oauth2.application;

import kr.co.toonzip.security.oauth2.domain.KakaoOAuth2UserInfo;
import kr.co.toonzip.security.oauth2.domain.UserPrincipal;
import kr.co.toonzip.security.oauth2.domain.OAuth2UserInfo;
import kr.co.toonzip.user.domain.User;
import kr.co.toonzip.user.domain.vo.ProviderType;
import kr.co.toonzip.user.domain.vo.Role;
import kr.co.toonzip.user.infrastructure.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;
    //private final OAuth2UserInfoFactory oAuth2UserInfoFactory;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest){
        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);

            return processOAuth2User(oAuth2UserRequest, oAuth2User);
    }

    // 사용자 정보 추출
    // oAuth2User.getAttributes(): 카카오로부터 받은 JSON 형태의 사용자 정보
    private OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
        String registrationId = oAuth2UserRequest.getClientRegistration().getRegistrationId();
        log.debug(registrationId + "!!!!!!!!!!!!!!!!11");
        //OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(registrationId, oAuth2User.getAttributes());
        OAuth2UserInfo oAuth2UserInfo = new KakaoOAuth2UserInfo(oAuth2User.getAttributes());
        User user = userRepository.findByEmail(oAuth2UserInfo.getEmail())
                .map(value -> updateUser(value, oAuth2UserInfo))
                .orElseGet(() -> registerUser(registrationId, oAuth2UserInfo));

        return UserPrincipal.create(user, oAuth2User.getAttributes());
    }

    private User registerUser(String registrationId, OAuth2UserInfo oAuth2UserInfo) {
        User user = userRepository.save(User.builder()
                .name(oAuth2UserInfo.getName())
                .email(oAuth2UserInfo.getEmail())
                .imageUrl(oAuth2UserInfo.getImageUrl())
                .role(Role.USER)
                .provider(ProviderType.valueOf(registrationId))
                .providerId(oAuth2UserInfo.getId())
                .build()
        );

        log.debug(user.getEmail() + "!!!!!!!!!!!!!!!");
        return user;
    }

    private User updateUser(User user, OAuth2UserInfo oAuth2UserInfo) {
        return userRepository.save(
                user.update(
                        oAuth2UserInfo.getName(),
                        oAuth2UserInfo.getImageUrl()
                )
        );
    }
}
