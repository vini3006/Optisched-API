package com.vinibarros.optisched.service;

import com.vinibarros.optisched.dto.request.UserRequest;
import com.vinibarros.optisched.dto.response.ProfessorResponse;
import com.vinibarros.optisched.entity.Institution;
import com.vinibarros.optisched.entity.Professor;
import com.vinibarros.optisched.entity.User;
import com.vinibarros.optisched.exception.DuplicateResourceException;
import com.vinibarros.optisched.mapper.ProfessorMapper;
import com.vinibarros.optisched.repository.InstitutionRepository;
import com.vinibarros.optisched.repository.ProfessorRepository;
import com.vinibarros.optisched.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProfessorService {

    private final ProfessorRepository professorRepository;
    private final InstitutionRepository institutionRepository;
    private final ProfessorMapper professorMapper;

    public ProfessorService(ProfessorRepository professorRepository, InstitutionRepository institutionRepository, ProfessorMapper professorMapper){
        this.professorRepository = professorRepository;
        this.institutionRepository = institutionRepository;
        this.professorMapper = professorMapper;
    }

    @Transactional
    public Professor create(UserRequest request, User savedUser, Institution institution) {
        Professor professor = professorMapper.toEntity(request, institution);
        professor.setUser(savedUser);

        return professorRepository.save(professor);
    }

    @Transactional(readOnly = true)
    public ProfessorResponse findById(Long id, Long institutionId){
        Professor professor = professorRepository.findByIdAndInstitutionId(id, institutionId)
                .orElseThrow(() -> new ResourceNotFoundException("Professor", id));
        return professorMapper.toResponse(professor);
    }

    @Transactional(readOnly = true)
    public List<ProfessorResponse> findAll(Long institutionId){
        return professorRepository.findAllByInstitutionId(institutionId)
                .stream()
                .map(professorMapper::toResponse)
                .toList();
    }

    @Transactional
    public ProfessorResponse update(Long id, String newName, Long institutionId){
        Professor professor = professorRepository.findByIdAndInstitutionId(id, institutionId)
                .orElseThrow(() -> new ResourceNotFoundException("Professor", id));

        professor.setName(newName);

        Professor updated = professorRepository.save(professor);
        return professorMapper.toResponse(updated);
    }

    @Transactional
    public void delete(Long id, Long institutionId){
        if(!professorRepository.existsByIdAndInstitutionId(id, institutionId)){
            throw new ResourceNotFoundException("Professor", id);
        }

        professorRepository.deleteById(id);
    }
}
