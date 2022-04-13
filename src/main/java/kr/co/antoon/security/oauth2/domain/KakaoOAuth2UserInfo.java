package kr.co.antoon.security.oauth2.domain;

import java.util.Map;

public class KakaoOAuth2UserInfo extends OAuth2UserInfo {

    public KakaoOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getId() {
        return attributes.get("id").toString();
    }

    @Override
    public String getName() {
        // kakao는 kakao_account에 유저 정보가 있다.
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        // kakao_account 안에 profile이라는 JSON객체가 있다. (nickname, profile_image_url)
        Map<String, Object> kakaoProfile = (Map<String, Object>) kakaoAccount.get("profile");
        return (String) kakaoProfile.get("nickname");
    }

    @Override
    public String getEmail() {
        // kakao는 kakao_account에 이메일 정보가 있다.
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        return (String) kakaoAccount.get("email");
    }

    @Override
    public String getImageUrl() {
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> kakaoProfile = (Map<String, Object>) kakaoAccount.get("profile");
        return (String) kakaoProfile.get("profile_image_url");
    }
}
