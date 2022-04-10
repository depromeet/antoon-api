package kr.co.toonzip.security.oauth2.domain;

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
        // kakao_account 안에 또 profile이라는 JSON객체가 있다. (nickname, profile_image_url)
        // 참고: https://developers.kakao.com/docs/latest/ko/kakaologin/rest-api#profile
        Map<String, Object> kakaoProfile = (Map<String, Object>) kakaoAccount.get("profile");
        return (String) kakaoProfile.get("nickname");
    }

    @Override
    public String getEmail() {
        // kakao는 kakao_account에 이메일 정보가 있다.
        // 참고: https://developers.kakao.com/docs/latest/ko/kakaologin/rest-api#kakao-account
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

//  HTTP/1.1 200 OK
//  {
//        "id":123456789,
//        "kakao_account": {
//        "profile_needs_agreement": false,
//        "profile": {
//          "nickname": "홍길동",
//          "thumbnail_image_url": "http://yyy.kakao.com/.../img_110x110.jpg",
//          "profile_image_url": "http://yyy.kakao.com/dn/.../img_640x640.jpg",
//          "is_default_image":false
//        },
//        "email_needs_agreement":false,
//        "is_email_valid": true,
//        "is_email_verified": true,
//        "email": "sample@sample.com",
//        "age_range_needs_agreement":false,
//        "age_range":"20~29",
//        "birthday_needs_agreement":false,
//        "birthday":"1130",
//        "gender_needs_agreement":false,
//        "gender":"female"
//   },
//   "properties":{
//        "nickname":"홍길동카톡",
//        "thumbnail_image":"http://xxx.kakao.co.kr/.../aaa.jpg",
//        "profile_image":"http://xxx.kakao.co.kr/.../bbb.jpg",
//        "custom_field1":"23",
//        "custom_field2":"여"
//        ...
//        }
//   }
