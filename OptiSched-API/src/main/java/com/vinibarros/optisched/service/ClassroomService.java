package com.vinibarros.optisched.service;

import com.vinibarros.optisched.dto.request.ClassroomRequest;
import com.vinibarros.optisched.dto.response.ClassroomResponse;
import com.vinibarros.optisched.entity.Classroom;
import com.vinibarros.optisched.entity.Institution;
import com.vinibarros.optisched.exception.DuplicateResourceException;
import com.vinibarros.optisched.exception.ResourceNotFoundException;
import com.vinibarros.optisched.mapper.ClassroomMapper;
import com.vinibarros.optisched.repository.ClassroomRepository;
import com.vinibarros.optisched.repository.InstitutionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClassroomService {

    private final ClassroomRepository classroomRepository;
    private final InstitutionRepository institutionRepository;
    private final ClassroomMapper classroomMapper;

    public ClassroomService(ClassroomRepository classroomRepository, InstitutionRepository institutionRepository, ClassroomMapper classroomMapper){
        this.classroomRepository = classroomRepository;
        this.institutionRepository = institutionRepository;
        this.classroomMapper = classroomMapper;
    }

    @Transactional
    public ClassroomResponse create(ClassroomRequest request, Long institutionId){
        if(classroomRepository.existsByNumberAndInstitutionId(request.number(), institutionId)){
            throw new DuplicateResourceException("Classroom", "number", request.number());
        }

        Institution institution = institutionRepository.findById(institutionId)
                .orElseThrow(() -> new ResourceNotFoundException("Institution", institutionId));

        Classroom classroom = classroomMapper.toEntity(request, institution);
        Classroom saved = classroomRepository.save(classroom);
        return classroomMapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public ClassroomResponse findById(Long id, Long institutionId){
        Classroom classroom = classroomRepository.findByIdAndInstitutionId(id, institutionId)
                .orElseThrow(() -> new ResourceNotFoundException("Classroom", id));

        return classroomMapper.toResponse(classroom);
    }

    @Transactional(readOnly = true)
    public List<ClassroomResponse> findAll(Long institutionId){
        return classroomRepository.findAllByInstitutionId(institutionId)
                .stream()
                .map(classroomMapper::toResponse)
                .toList();
    }

    @Transactional
    public ClassroomResponse update(Long id, ClassroomRequest request, Long institutionId){
        Classroom classroom = classroomRepository.findByIdAndInstitutionId(id, institutionId)
                .orElseThrow(() -> new ResourceNotFoundException("Classroom", id));

        if(!classroom.getNumber().equals(request.number()) && classroomRepository.existsByNumberAndInstitutionId(request.number(), institutionId)){
            throw new DuplicateResourceException("Classroom", "number", request.number());
        }

        classroom.setNumber(request.number());
        classroom.setCapacity(request.capacity());

        Classroom updated = classroomRepository.save(classroom);
        return classroomMapper.toResponse(updated);
    }

    @Transactional
    public void delete(Long id, Long institutionId){
        if(!classroomRepository.existsByIdAndInstitutionId(id, institutionId)){
            throw new ResourceNotFoundException("Classroom", id);
        }

        classroomRepository.deleteById(id);
    }
}
