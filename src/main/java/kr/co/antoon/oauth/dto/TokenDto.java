package kr.co.antoon.oauth.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@NoArgsConstructor
@Getter
public class TokenDto {
    private String token;
    private String refreshToken;

    public TokenDto(String token, String refreshToken) {
        this.token = token;
        this.refreshToken = refreshToken;
    }
}
