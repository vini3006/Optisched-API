package com.vinibarros.optisched.service;

import com.vinibarros.optisched.dto.request.SubjectOfferingRequest;
import com.vinibarros.optisched.dto.response.SubjectOfferingResponse;
import com.vinibarros.optisched.entity.*;
import com.vinibarros.optisched.exception.DuplicateResourceException;
import com.vinibarros.optisched.exception.ResourceNotFoundException;
import com.vinibarros.optisched.mapper.SubjectOfferingMapper;
import com.vinibarros.optisched.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SubjectOfferingService {

    private final SubjectOfferingRepository subjectOfferingRepository;
    private final CourseRepository courseRepository;
    private final SubjectRepository subjectRepository;
    private final SemesterRepository semesterRepository;
    private final InstitutionRepository institutionRepository;
    private final SubjectOfferingMapper subjectOfferingMapper;

    public SubjectOfferingService(SubjectOfferingRepository subjectOfferingRepository, CourseRepository courseRepository, SubjectRepository subjectRepository, SemesterRepository semesterRepository, InstitutionRepository institutionRepository, SubjectOfferingMapper subjectOfferingMapper){
        this.subjectOfferingRepository = subjectOfferingRepository;
        this.courseRepository = courseRepository;
        this.subjectRepository = subjectRepository;
        this.semesterRepository = semesterRepository;
        this.institutionRepository = institutionRepository;
        this.subjectOfferingMapper = subjectOfferingMapper;
    }

    @Transactional
    public SubjectOfferingResponse create(SubjectOfferingRequest request, Long institutionId){
        if(subjectOfferingRepository.existsByCourseIdAndSubjectIdAndSemesterIdAndSectionAndInstitutionId(request.courseId(), request.subjectId(), request.semesterId(), request.section(), institutionId)){
            throw new DuplicateResourceException("SubjectOffering already exists with these attributes.");
        }

        Course course = courseRepository.findByIdAndInstitutionId(request.courseId(), institutionId)
                .orElseThrow(() -> new ResourceNotFoundException("Course", request.courseId()));
        Subject subject = subjectRepository.findByIdAndInstitutionId(request.subjectId(), institutionId)
                .orElseThrow(() -> new ResourceNotFoundException("Subject", request.subjectId()));
        Semester semester = semesterRepository.findByIdAndInstitutionId(request.semesterId(), institutionId)
                .orElseThrow(() -> new ResourceNotFoundException("Semester", request.semesterId()));
        Institution institution = institutionRepository.findById(institutionId)
                .orElseThrow(() -> new ResourceNotFoundException("Institution", institutionId));

        SubjectOffering subjectOffering = subjectOfferingMapper.toEntity(request, course, subject, semester, institution);
        SubjectOffering saved = subjectOfferingRepository.save(subjectOffering);
        return subjectOfferingMapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public SubjectOfferingResponse findById(Long id, Long institutionId){
        SubjectOffering subjectOffering = subjectOfferingRepository.findByIdAndInstitutionId(id, institutionId)
                .orElseThrow(() -> new ResourceNotFoundException("SubjectOffering", id));
        return subjectOfferingMapper.toResponse(subjectOffering);
    }

    @Transactional(readOnly = true)
    public List<SubjectOfferingResponse> findAll(Long institutionId){
        return subjectOfferingRepository.findAllByInstitutionId(institutionId)
                .stream()
                .map(subjectOfferingMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<SubjectOfferingResponse> findByCourse(Long courseId, Long institutionId){
        if(!courseRepository.existsByIdAndInstitutionId(courseId, institutionId)){
            throw new ResourceNotFoundException("Course", courseId);
        }
        return subjectOfferingRepository.findByCourseId(courseId)
                .stream()
                .map(subjectOfferingMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<SubjectOfferingResponse> findBySubject(Long subjectId, Long institutionId){
        if(!subjectRepository.existsByIdAndInstitutionId(subjectId, institutionId)){
            throw new ResourceNotFoundException("Subject", subjectId);
        }
        return subjectOfferingRepository.findBySubjectId(subjectId)
                .stream()
                .map(subjectOfferingMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<SubjectOfferingResponse> findBySemester(Long semesterId, Long institutionId){
        if(!semesterRepository.existsByIdAndInstitutionId(semesterId, institutionId)){
            throw new ResourceNotFoundException("Semester", semesterId);
        }
        return subjectOfferingRepository.findBySemesterId(semesterId)
                .stream()
                .map(subjectOfferingMapper::toResponse)
                .toList();
    }

    @Transactional
    public SubjectOfferingResponse update(Long id, SubjectOfferingRequest request, Long institutionId){
        SubjectOffering subjectOffering = subjectOfferingRepository.findByIdAndInstitutionId(id, institutionId)
                .orElseThrow(() -> new ResourceNotFoundException("SubjectOffering", id));

        Course course = courseRepository.findByIdAndInstitutionId(request.courseId(), institutionId)
                .orElseThrow(() -> new ResourceNotFoundException("Course", request.courseId()));
        Subject subject = subjectRepository.findByIdAndInstitutionId(request.subjectId(), institutionId)
                .orElseThrow(() -> new ResourceNotFoundException("Subject", request.subjectId()));
        Semester semester = semesterRepository.findByIdAndInstitutionId(request.semesterId(), institutionId)
                .orElseThrow(() -> new ResourceNotFoundException("Semester", request.semesterId()));

        boolean combinationChanged =
                !subjectOffering.getCourse().getId().equals(request.courseId()) ||
                        !subjectOffering.getSubject().getId().equals(request.subjectId()) ||
                        !subjectOffering.getSemester().getId().equals(request.semesterId()) ||
                        !subjectOffering.getSection().equals(request.section());

        if(combinationChanged && subjectOfferingRepository.existsByCourseIdAndSubjectIdAndSemesterIdAndSectionAndInstitutionId
                (request.courseId(), request.subjectId(), request.semesterId(), request.section(), institutionId)) {
            throw new DuplicateResourceException("SubjectOffering already exists for this course, subject, semester and section combination");
        }

        subjectOffering.setCourse(course);
        subjectOffering.setSubject(subject);
        subjectOffering.setSemester(semester);
        subjectOffering.setSection(request.section());
        subjectOffering.setExpectedStudents(request.expectedStudents());
        subjectOffering.setRecommendedSemester(request.recommendedSemester());

        SubjectOffering updated = subjectOfferingRepository.save(subjectOffering);
        return subjectOfferingMapper.toResponse(updated);
    }

    @Transactional
    public void delete(Long id, Long institutionId){
        if(!subjectOfferingRepository.existsByIdAndInstitutionId(id, institutionId)){
            throw new ResourceNotFoundException("SubjectOffering", id);
        }
        subjectOfferingRepository.deleteById(id);
    }
}

