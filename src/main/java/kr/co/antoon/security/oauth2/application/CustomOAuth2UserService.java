package kr.co.antoon.security.oauth2.application;

import kr.co.antoon.security.oauth2.domain.KakaoOAuth2UserInfo;
import kr.co.antoon.security.oauth2.domain.UserPrincipal;
import kr.co.antoon.security.oauth2.domain.OAuth2UserInfo;
import kr.co.antoon.security.token.JwtTokenProvider;
import kr.co.antoon.user.domain.User;
import kr.co.antoon.user.domain.vo.Role;
import kr.co.antoon.user.infrastructure.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest){
        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);
        log.info("registrationId = {}",  oAuth2UserRequest.getClientRegistration().getRegistrationId());
        return processOAuth2User(oAuth2User);
    }

    // 사용자 정보 추출
    // oAuth2User.getAttributes(): 카카오로부터 받은 JSON 형태의 사용자 정보
    private OAuth2User processOAuth2User(OAuth2User oAuth2User) {
        OAuth2UserInfo oAuth2UserInfo = new KakaoOAuth2UserInfo(oAuth2User.getAttributes());
        User user = userRepository.findByEmail(oAuth2UserInfo.getEmail())
                .map(value -> updateUser(value, oAuth2UserInfo))
                .orElseGet(() -> registerUser(oAuth2UserInfo));

        return UserPrincipal.create(user, oAuth2User.getAttributes());
    }

    // 최초 로그인 회원가입
    private User registerUser(OAuth2UserInfo oAuth2UserInfo) {
        String refreshToken = jwtTokenProvider.createRefreshToken(oAuth2UserInfo.getEmail());
        return userRepository.save(User.builder()
                .name(oAuth2UserInfo.getName())
                .email(oAuth2UserInfo.getEmail())
                .imageUrl(oAuth2UserInfo.getImageUrl())
                .role(Role.USER)
                .refreshToken(refreshToken)
                .build()
        );
    }

    // 회원 업데이트
    private User updateUser(User user, OAuth2UserInfo oAuth2UserInfo) {
        String refreshToken = jwtTokenProvider.createRefreshToken(oAuth2UserInfo.getEmail());
        return userRepository.save(
                user.update(
                        oAuth2UserInfo.getName(),
                        oAuth2UserInfo.getImageUrl(),
                        refreshToken
                )
        );
    }
}
