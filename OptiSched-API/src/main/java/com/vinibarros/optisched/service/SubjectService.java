package com.vinibarros.optisched.service;

import com.vinibarros.optisched.dto.request.SubjectRequest;
import com.vinibarros.optisched.dto.response.SubjectResponse;
import com.vinibarros.optisched.entity.Institution;
import com.vinibarros.optisched.entity.Subject;
import com.vinibarros.optisched.exception.DuplicateResourceException;
import com.vinibarros.optisched.exception.ResourceNotFoundException;
import com.vinibarros.optisched.mapper.SubjectMapper;
import com.vinibarros.optisched.repository.InstitutionRepository;
import com.vinibarros.optisched.repository.SubjectRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SubjectService {

    private final SubjectRepository subjectRepository;
    private final InstitutionRepository institutionRepository;
    private final SubjectMapper subjectMapper;

    public SubjectService(SubjectRepository subjectRepository, InstitutionRepository institutionRepository, SubjectMapper subjectMapper) {
        this.subjectRepository = subjectRepository;
        this.institutionRepository = institutionRepository;
        this.subjectMapper = subjectMapper;
    }

    @Transactional
    public SubjectResponse create(SubjectRequest request, Long institutionId){
        if(subjectRepository.existsByCodeAndInstitutionId(request.code(), institutionId)){
            throw new DuplicateResourceException("Subject", "code", request.code());
        }

        Institution institution = institutionRepository.findById(institutionId)
                .orElseThrow(() -> new ResourceNotFoundException("Institution", institutionId));

        Subject subject = subjectMapper.toEntity(request, institution);
        Subject saved = subjectRepository.save(subject);
        return subjectMapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public SubjectResponse findById(Long id, Long institutionId){
        Subject subject = subjectRepository.findByIdAndInstitutionId(id, institutionId)
                .orElseThrow(() -> new ResourceNotFoundException("Subject", id));
        return subjectMapper.toResponse(subject);
    }

    @Transactional(readOnly = true)
    public List<SubjectResponse> findAll(Long institutionId){
        return subjectRepository.findAllByInstitutionId(institutionId)
                .stream()
                .map(subjectMapper::toResponse)
                .toList();
    }

    @Transactional
    public SubjectResponse update(Long id, SubjectRequest request, Long institutionId){
        Subject subject = subjectRepository.findByIdAndInstitutionId(id, institutionId)
                .orElseThrow(() -> new ResourceNotFoundException("Subject", id));

        if (!subject.getCode().equals(request.code()) && subjectRepository.existsByCodeAndInstitutionId(request.code(), institutionId)) {
            throw new DuplicateResourceException("Subject", "code", request.code());
        }

        subject.setCode(request.code());
        subject.setName(request.name());
        subject.setWorkload(request.workload());

        Subject updated = subjectRepository.save(subject);
        return subjectMapper.toResponse(updated);
    }

    @Transactional
    public void delete(Long id, Long institutionId){
        if(!subjectRepository.existsByIdAndInstitutionId(id, institutionId)){
            throw new ResourceNotFoundException("Subject", id);
        }

        subjectRepository.deleteById(id);
    }
}
