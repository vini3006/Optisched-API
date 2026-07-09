package com.vinibarros.optisched.service;

import com.vinibarros.optisched.dto.request.SubjectRequest;
import com.vinibarros.optisched.dto.response.SubjectResponse;
import com.vinibarros.optisched.entity.Subject;
import com.vinibarros.optisched.exception.DuplicateResourceException;
import com.vinibarros.optisched.exception.ResourceNotFoundException;
import com.vinibarros.optisched.mapper.SubjectMapper;
import com.vinibarros.optisched.repository.SubjectRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SubjectService {

    private final SubjectRepository subjectRepository;
    private final SubjectMapper subjectMapper;

    public SubjectService(SubjectRepository subjectRepository, SubjectMapper subjectMapper) {
        this.subjectRepository = subjectRepository;
        this.subjectMapper = subjectMapper;
    }

    @Transactional
    public SubjectResponse create(SubjectRequest request){
        if(subjectRepository.existsByCode(request.code())){
            throw new DuplicateResourceException("Subject", "code", request.code());
        }
        Subject subject = subjectMapper.toEntity(request);
        Subject saved = subjectRepository.save(subject);
        return subjectMapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public SubjectResponse findById(Long id){
        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Subject", id));
        return subjectMapper.toResponse(subject);
    }

    @Transactional(readOnly = true)
    public List<SubjectResponse> findAll(){
        return subjectRepository.findAll()
                .stream()
                .map(subjectMapper::toResponse)
                .toList();
    }

    @Transactional
    public SubjectResponse update(Long id, SubjectRequest request){
        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Subject", id));

        if (!subject.getCode().equals(request.code()) && subjectRepository.existsByCode(request.code())) {
            throw new DuplicateResourceException("Subject", "code", request.code());
        }

        subject.setCode(request.code());
        subject.setName(request.name());
        subject.setWorkload(request.workload());

        Subject updated = subjectRepository.save(subject);
        return subjectMapper.toResponse(updated);
    }

    @Transactional
    public void delete(Long id){
        if(!subjectRepository.existsById(id)){
            throw new ResourceNotFoundException("Subject", id);
        }

        subjectRepository.deleteById(id);
    }
}
