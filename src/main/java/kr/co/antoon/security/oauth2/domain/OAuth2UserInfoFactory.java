//package kr.co.toonzip.security.oauth2.domain;
//
//import java.util.Map;
//
//// 추후 다양한 플랫폼의 소셜로그인 지원하기 위해 생성한 클래스
//// exception 처리 수정 필요
//public class OAuth2UserInfoFactory {
//
//    public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
//        if(registrationId.equalsIgnoreCase("kakao")) {
//            return new KakaoOAuth2UserInfo(attributes);
//        } else {
//            throw new IllegalArgumentException(registrationId + "로그인은 지원하지 않습니다.");
//        }
//    }
//}
