package com.vinibarros.optisched.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record SubjectRequest(
    @NotBlank String code,
    @NotBlank String name,
    @NotNull @Positive Integer workload
)
{}
