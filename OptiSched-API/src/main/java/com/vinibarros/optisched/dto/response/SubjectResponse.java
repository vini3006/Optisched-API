package com.vinibarros.optisched.dto.response;

public record SubjectResponse(
        Long id,
        String code,
        String name,
        Integer workload
)
{}
