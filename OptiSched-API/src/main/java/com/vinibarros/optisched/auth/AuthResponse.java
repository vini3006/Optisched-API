package com.vinibarros.optisched.auth;

public record AuthResponse(
        String token,
        String type, // Ex: "Bearer"
        Long userId,
        String email,
        String role,
        Long institutionId
) {}
