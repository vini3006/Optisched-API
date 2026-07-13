package com.vinibarros.optisched.mapper;

import com.vinibarros.optisched.dto.request.ProfessorRequest;
import com.vinibarros.optisched.dto.response.ProfessorResponse;
import com.vinibarros.optisched.entity.Professor;
import org.springframework.stereotype.Component;

@Component
public class ProfessorMapper {

    public Professor toEntity(ProfessorRequest request) {
        Professor professor = new Professor();
        professor.setName(request.name());
        professor.setEmail(request.email());
        return professor;
    }

    public ProfessorResponse toResponse(Professor professor) {
        return new ProfessorResponse(
                professor.getId(),
                professor.getName(),
                professor.getEmail()
        );
    }
}

