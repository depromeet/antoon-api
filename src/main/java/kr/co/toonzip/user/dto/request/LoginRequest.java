package kr.co.toonzip.user.dto.request;

public record LoginRequest (
        String email,
        String password
) {}
