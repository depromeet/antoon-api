package kr.co.antoon.oauth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class KakaoOauthToken {
    private String access_token;
    private String token_type;
    private String refresh_token;
    private int expires_in;
    private String scope;
    private int refresh_token_expires_in;

    @Builder
    public KakaoOauthToken(String access_token, String token_type, String refresh_token, int expires_in,
                           String scope, int refresh_token_expires_in) {
        this.access_token = access_token;
        this.token_type = token_type;
        this.refresh_token = refresh_token;
        this.expires_in = expires_in;
        this.scope = scope;
        this.refresh_token_expires_in = refresh_token_expires_in;
    }
}
