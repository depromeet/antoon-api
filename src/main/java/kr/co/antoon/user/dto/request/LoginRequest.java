package kr.co.antoon.user.dto.request;

public record LoginRequest (
        String email,
        String password
) {}
