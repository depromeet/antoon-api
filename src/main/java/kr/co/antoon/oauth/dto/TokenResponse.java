package kr.co.antoon.oauth.dto;

public record TokenResponse (
        String accessToken,
        String refreshToken
) {}
