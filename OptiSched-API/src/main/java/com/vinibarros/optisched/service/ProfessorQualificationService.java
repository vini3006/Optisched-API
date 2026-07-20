package com.vinibarros.optisched.service;

import com.vinibarros.optisched.dto.request.ProfessorQualificationRequest;
import com.vinibarros.optisched.dto.response.ProfessorQualificationResponse;
import com.vinibarros.optisched.entity.*;
import com.vinibarros.optisched.exception.DuplicateResourceException;
import com.vinibarros.optisched.exception.ResourceNotFoundException;
import com.vinibarros.optisched.mapper.ProfessorQualificationMapper;
import com.vinibarros.optisched.repository.InstitutionRepository;
import com.vinibarros.optisched.repository.ProfessorQualificationRepository;
import com.vinibarros.optisched.repository.ProfessorRepository;
import com.vinibarros.optisched.repository.SubjectRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProfessorQualificationService {

    private final ProfessorQualificationRepository qualificationRepository;
    private final ProfessorRepository professorRepository;
    private final SubjectRepository subjectRepository;
    private final InstitutionRepository institutionRepository;
    private final ProfessorQualificationMapper qualificationMapper;

    public ProfessorQualificationService (ProfessorQualificationRepository qualificationRepository, ProfessorRepository professorRepository, SubjectRepository subjectRepository, InstitutionRepository institutionRepository, ProfessorQualificationMapper qualificationMapper){
        this.qualificationRepository = qualificationRepository;
        this.professorRepository = professorRepository;
        this.subjectRepository = subjectRepository;
        this.institutionRepository = institutionRepository;
        this.qualificationMapper = qualificationMapper;
    }

    @Transactional
    public ProfessorQualificationResponse create(ProfessorQualificationRequest request, Long institutionId){
        ProfessorQualificationId id = new ProfessorQualificationId(request.professorId(), request.subjectId());

        if(qualificationRepository.existsByIdAndInstitutionId(id, institutionId)) {
            throw new DuplicateResourceException("ProfessorQualification already exists for professorId=" + request.professorId() + " and subjectId=" + request.subjectId());
        }

        Professor professor = professorRepository.findByIdAndInstitutionId(request.professorId(), institutionId)
                .orElseThrow(() -> new ResourceNotFoundException("Professor", request.professorId()));

        Subject subject = subjectRepository.findByIdAndInstitutionId(request.subjectId(), institutionId)
                .orElseThrow(() -> new ResourceNotFoundException("Subject", request.subjectId()));

        Institution institution = institutionRepository.findById(institutionId)
                .orElseThrow(() -> new ResourceNotFoundException("Institution", institutionId));

        ProfessorQualification professorQualification = qualificationMapper.toEntity(professor, subject, institution);
        ProfessorQualification saved = qualificationRepository.save(professorQualification);
        return qualificationMapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<ProfessorQualificationResponse> findAll(Long institutionId) {
        return qualificationRepository.findAllByInstitutionId(institutionId)
                .stream()
                .map(qualificationMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ProfessorQualificationResponse> findByProfessor(Long professorId, Long institutionId){
        if(!professorRepository.existsByIdAndInstitutionId(professorId, institutionId)) {
            throw new ResourceNotFoundException("Professor", professorId);
        }

        return qualificationRepository.findById_ProfessorIdAndInstitutionId(professorId, institutionId)
                .stream()
                .map(qualificationMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ProfessorQualificationResponse> findBySubject(Long subjectId, Long institutionId){
        if(!subjectRepository.existsByIdAndInstitutionId(subjectId, institutionId)){
            throw new ResourceNotFoundException("Subject", subjectId);
        }

        return qualificationRepository.findById_SubjectIdAndInstitutionId(subjectId, institutionId)
                .stream()
                .map(qualificationMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ProfessorQualificationResponse> findByProfessorAndSubject(Long professorId, Long subjectId, Long institutionId){
        ProfessorQualificationId id = new ProfessorQualificationId(professorId, subjectId);

        if (!qualificationRepository.existsByIdAndInstitutionId(id, institutionId)) {
            throw new ResourceNotFoundException(
                    "ProfessorQualification not found for professorId=" + professorId + " and subjectId=" + subjectId
            );
        }

        return qualificationRepository.findById_ProfessorIdAndId_SubjectIdAndInstitutionId(professorId, subjectId, institutionId)
                .stream()
                .map(qualificationMapper::toResponse)
                .toList();
    }

    @Transactional
    public void delete(Long professorId, Long subjectId, Long institutionId) {
        ProfessorQualificationId id = new ProfessorQualificationId(professorId, subjectId);
        if (!qualificationRepository.existsByIdAndInstitutionId(id, institutionId)) {
            throw new ResourceNotFoundException(
                    "ProfessorQualification not found for professorId=" + professorId + " and subjectId=" + subjectId
            );
        }
        qualificationRepository.deleteById(id);
    }
}
