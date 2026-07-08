package com.vinibarros.optisched.service;

import com.vinibarros.optisched.dto.request.ProfessorRequest;
import com.vinibarros.optisched.dto.response.ProfessorResponse;
import com.vinibarros.optisched.entity.Professor;
import com.vinibarros.optisched.exception.DuplicateResourceException;
import com.vinibarros.optisched.mapper.ProfessorMapper;
import com.vinibarros.optisched.repository.ProfessorRepository;
import com.vinibarros.optisched.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProfessorService {

    private final ProfessorRepository professorRepository;
    private final ProfessorMapper professorMapper;

    public ProfessorService(ProfessorRepository professorRepository, ProfessorMapper professorMapper){
        this.professorRepository = professorRepository;
        this.professorMapper = professorMapper;
    }

    @Transactional
    public ProfessorResponse create(ProfessorRequest request){
        if(professorRepository.existsByEmail(request.email())){
            throw new DuplicateResourceException("Professor", "email", request.email());
        }
        Professor professor = professorMapper.toEntity(request);
        Professor saved = professorRepository.save(professor);
        return professorMapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public ProfessorResponse findById(Long id){
        Professor professor = professorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Professor", id));
        return professorMapper.toResponse(professor);
    }

    @Transactional(readOnly = true)
    public List<ProfessorResponse> findAll(){
        return professorRepository.findAll()
                .stream()
                .map(professorMapper::toResponse)
                .toList();
    }

    @Transactional
    public ProfessorResponse update(Long id, ProfessorRequest request){
        Professor professor = professorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Professor", id));

        professor.setName(request.name());
        professor.setEmail(request.email());

        Professor updated = professorRepository.save(professor);
        return professorMapper.toResponse(updated);
    }

    @Transactional
    public void delete(Long id){
        if(!professorRepository.existsById(id)){
            throw new ResourceNotFoundException("Professor", id);
        }

        professorRepository.deleteById(id);
    }
}
