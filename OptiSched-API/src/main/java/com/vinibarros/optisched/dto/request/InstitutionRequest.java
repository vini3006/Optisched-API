package com.vinibarros.optisched.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record InstitutionRequest(
    @NotBlank String name,
    @Size(min=14, max=14) String cnpj
)
{}
