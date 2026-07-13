package com.vinibarros.optisched.service;

import com.vinibarros.optisched.dto.request.SubjectOfferingRequest;
import com.vinibarros.optisched.dto.response.SubjectOfferingResponse;
import com.vinibarros.optisched.entity.Course;
import com.vinibarros.optisched.entity.Semester;
import com.vinibarros.optisched.entity.Subject;
import com.vinibarros.optisched.entity.SubjectOffering;
import com.vinibarros.optisched.exception.DuplicateResourceException;
import com.vinibarros.optisched.exception.ResourceNotFoundException;
import com.vinibarros.optisched.mapper.SubjectOfferingMapper;
import com.vinibarros.optisched.repository.CourseRepository;
import com.vinibarros.optisched.repository.SemesterRepository;
import com.vinibarros.optisched.repository.SubjectOfferingRepository;
import com.vinibarros.optisched.repository.SubjectRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SubjectOfferingService {

    private final SubjectOfferingRepository subjectOfferingRepository;
    private final CourseRepository courseRepository;
    private final SubjectRepository subjectRepository;
    private final SemesterRepository semesterRepository;
    private final SubjectOfferingMapper subjectOfferingMapper;

    public SubjectOfferingService(SubjectOfferingRepository subjectOfferingRepository, CourseRepository courseRepository, SubjectRepository subjectRepository, SemesterRepository semesterRepository, SubjectOfferingMapper subjectOfferingMapper){
        this.subjectOfferingRepository = subjectOfferingRepository;
        this.courseRepository = courseRepository;
        this.subjectRepository = subjectRepository;
        this.semesterRepository = semesterRepository;
        this.subjectOfferingMapper = subjectOfferingMapper;
    }

    @Transactional
    public SubjectOfferingResponse create(SubjectOfferingRequest request){
        if(subjectOfferingRepository.existsByCourseIdAndSubjectIdAndSemesterIdAndSection(request.courseId(), request.subjectId(), request.semesterId(), request.section())){
            throw new DuplicateResourceException("SubjectOffering already exists with these attributes.");
        }

        Course course = courseRepository.findById(request.courseId())
                .orElseThrow(() -> new ResourceNotFoundException("Course", request.courseId()));
        Subject subject = subjectRepository.findById(request.subjectId())
                .orElseThrow(() -> new ResourceNotFoundException("Subject", request.subjectId()));
        Semester semester = semesterRepository.findById(request.semesterId())
                .orElseThrow(() -> new ResourceNotFoundException("Semester", request.semesterId()));

        SubjectOffering subjectOffering = subjectOfferingMapper.toEntity(request, course, subject, semester);
        SubjectOffering saved = subjectOfferingRepository.save(subjectOffering);
        return subjectOfferingMapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public SubjectOfferingResponse findById(Long id){
        SubjectOffering subjectOffering = subjectOfferingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("SubjectOffering", id));
        return subjectOfferingMapper.toResponse(subjectOffering);
    }

    @Transactional(readOnly = true)
    public List<SubjectOfferingResponse> findAll(){
        return subjectOfferingRepository.findAll()
                .stream()
                .map(subjectOfferingMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<SubjectOfferingResponse> findByCourse(Long courseId){
        if(!courseRepository.existsById(courseId)){
            throw new ResourceNotFoundException("Course", courseId);
        }
        return subjectOfferingRepository.findByCourseId(courseId)
                .stream()
                .map(subjectOfferingMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<SubjectOfferingResponse> findBySubject(Long subjectId){
        if(!subjectRepository.existsById(subjectId)){
            throw new ResourceNotFoundException("Subject", subjectId);
        }
        return subjectOfferingRepository.findBySubjectId(subjectId)
                .stream()
                .map(subjectOfferingMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<SubjectOfferingResponse> findBySemester(Long semesterId){
        if(!semesterRepository.existsById(semesterId)){
            throw new ResourceNotFoundException("Semester", semesterId);
        }
        return subjectOfferingRepository.findBySemesterId(semesterId)
                .stream()
                .map(subjectOfferingMapper::toResponse)
                .toList();
    }

    @Transactional
    public SubjectOfferingResponse update(Long id, SubjectOfferingRequest request){
        SubjectOffering subjectOffering = subjectOfferingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("SubjectOffering", id));

        Course course = courseRepository.findById(request.courseId())
                .orElseThrow(() -> new ResourceNotFoundException("Course", request.courseId()));

        Subject subject = subjectRepository.findById(request.subjectId())
                .orElseThrow(() -> new ResourceNotFoundException("Subject", request.subjectId()));

        Semester semester = semesterRepository.findById(request.semesterId())
                .orElseThrow(() -> new ResourceNotFoundException("Semester", request.semesterId()));

        boolean combinationChanged =
               !subjectOffering.getCourse().getId().equals(request.courseId()) ||
               !subjectOffering.getSubject().getId().equals(request.subjectId()) ||
               !subjectOffering.getSemester().getId().equals(request.semesterId()) ||
               !subjectOffering.getSection().equals(request.section());

        if(combinationChanged && subjectOfferingRepository.existsByCourseIdAndSubjectIdAndSemesterIdAndSection
                (request.courseId(), request.subjectId(), request.semesterId(), request.section())) {
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
    public void delete(Long id){
        if(!subjectOfferingRepository.existsById(id)){
            throw new ResourceNotFoundException("SubjectOffering", id);
        }
        subjectOfferingRepository.deleteById(id);
    }
}

