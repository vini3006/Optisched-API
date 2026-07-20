package com.vinibarros.optisched.dto.response;

import com.vinibarros.optisched.enums.UserRole;

public record UserResponse(
        Long id,
        String name,
        String email,
        UserRole role
)
{}
