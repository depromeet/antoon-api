package kr.co.antoon.oauth.dto;

import kr.co.antoon.error.dto.ErrorMessage;
import kr.co.antoon.error.exception.common.NotExistsException;
import kr.co.antoon.user.domain.User;
import kr.co.antoon.user.domain.vo.Role;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.Map;

@ToString
@Builder(access = AccessLevel.PRIVATE)
@Getter
public class OAuth2Attribute {
    private Map<String, Object> attributes;
    private String attributeKey;
    private String email;
    private String name;
    private String imageUrl;
    private String gender;
    private String ageRange;

    // Platform enum으로 분기 해결 가능
    public static OAuth2Attribute of(String provider, Map<String, Object> attributes) {
        return switch (provider) {
            case "kakao" -> ofKakao("email", attributes);
            case "google" -> ofGoogle("sub", attributes);
            default -> throw new NotExistsException(ErrorMessage.NOT_EXISTS_OAUTH_INFO);
        };
    }

    protected static OAuth2Attribute ofKakao(String attributeKey, Map<String, Object> attributes) {
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> kakaoProfile = (Map<String, Object>) kakaoAccount.get("profile");

        return OAuth2Attribute.builder()
                .name((String) kakaoProfile.get("nickname"))
                .email((String) kakaoAccount.get("email"))
                .imageUrl((String) kakaoProfile.get("profile_image_url"))
                .gender((String) kakaoAccount.get("gender"))
                .ageRange((String) kakaoAccount.get("age_range"))
                .attributes(kakaoAccount)
                .attributeKey(attributeKey)
                .build();
    }

    private static OAuth2Attribute ofGoogle(String attributeKey,
                                            Map<String, Object> attributes) {
        return OAuth2Attribute.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .imageUrl((String) attributes.get("picture"))
                .attributes(attributes)
                .attributeKey(attributeKey)
                .build();
    }

    public User toEntity() {
        return User.builder()
                .name(name)
                .email(email)
                .imageUrl(imageUrl)
                .role(Role.USER)
                .build();
    }
}
