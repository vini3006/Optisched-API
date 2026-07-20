package com.vinibarros.optisched.service;

import com.vinibarros.optisched.dto.request.AvailabilityRequest;
import com.vinibarros.optisched.dto.response.AvailabilityResponse;
import com.vinibarros.optisched.entity.*;
import com.vinibarros.optisched.exception.DuplicateResourceException;
import com.vinibarros.optisched.exception.ResourceNotFoundException;
import com.vinibarros.optisched.mapper.AvailabilityMapper;
import com.vinibarros.optisched.repository.AvailabilityRepository;
import com.vinibarros.optisched.repository.InstitutionRepository;
import com.vinibarros.optisched.repository.ProfessorRepository;
import com.vinibarros.optisched.repository.TimeSlotRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AvailabilityService {

    private final AvailabilityRepository availabilityRepository;
    private final ProfessorRepository professorRepository;
    private final TimeSlotRepository timeSlotRepository;
    private final InstitutionRepository institutionRepository;
    private final AvailabilityMapper availabilityMapper;

    public AvailabilityService(AvailabilityRepository availabilityRepository, ProfessorRepository professorRepository, TimeSlotRepository timeSlotRepository, InstitutionRepository institutionRepository, AvailabilityMapper availabilityMapper){
        this.availabilityRepository = availabilityRepository;
        this.professorRepository = professorRepository;
        this.timeSlotRepository = timeSlotRepository;
        this.institutionRepository = institutionRepository;
        this.availabilityMapper = availabilityMapper;
    }

    @Transactional
    public AvailabilityResponse create(AvailabilityRequest request, Long institutionId){
        AvailabilityId id = new AvailabilityId(request.professorId(), request.timeSlotId());

        Institution institution = institutionRepository.findById(institutionId)
                .orElseThrow(() -> new ResourceNotFoundException("Institution", institutionId));

        if(availabilityRepository.existsByIdAndInstitutionId(id, institutionId)){
            throw new DuplicateResourceException("Availability already exists for professorId= " + request.professorId() + " and TimeSlotId= " + request.timeSlotId());
        }

        Professor professor = professorRepository.findByIdAndInstitutionId(request.professorId(), institutionId)
                .orElseThrow(() -> new ResourceNotFoundException("Professor", request.professorId()));

        TimeSlot timeSlot = timeSlotRepository.findByIdAndInstitutionId(request.timeSlotId(), institutionId)
                .orElseThrow(() -> new ResourceNotFoundException("TimeSlot", request.timeSlotId()));

        Availability availability = availabilityMapper.toEntity(professor, timeSlot, institution);
        Availability saved = availabilityRepository.save(availability);
        return availabilityMapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<AvailabilityResponse> findAll(Long institutionId){
        return availabilityRepository.findAllByInstitutionId(institutionId)
                .stream()
                .map(availabilityMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<AvailabilityResponse> findByProfessor(Long professorId, Long institutionId){
        if(!professorRepository.existsByIdAndInstitutionId(professorId, institutionId)) {
            throw new ResourceNotFoundException("Professor", professorId);
        }

        return availabilityRepository.findById_ProfessorIdAndInstitutionId(professorId, institutionId)
                .stream()
                .map(availabilityMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<AvailabilityResponse> findByTimeSlot(Long timeSlotId, Long institutionId){
        if(!timeSlotRepository.existsByIdAndInstitutionId(timeSlotId, institutionId)){
            throw new ResourceNotFoundException("TimeSlot", timeSlotId);
        }

        return availabilityRepository.findById_TimeSlotIdAndInstitutionId(timeSlotId, institutionId)
                .stream()
                .map(availabilityMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<AvailabilityResponse> findByProfessorAndTimeSlot(Long professorId, Long timeSlotId, Long institutionId){
        AvailabilityId id = new AvailabilityId(professorId, timeSlotId);
        if(!availabilityRepository.existsByIdAndInstitutionId(id, institutionId)){
            throw new ResourceNotFoundException("Availability not found for professorId=" + professorId + " and timeSlotId=" + timeSlotId);
        }

        return availabilityRepository.findById_ProfessorIdAndId_TimeSlotIdAndInstitutionId(professorId, timeSlotId, institutionId)
                .stream()
                .map(availabilityMapper::toResponse)
                .toList();
    }

    @Transactional
    public void delete(Long professorId, Long timeSlotId, Long institutionId){
        AvailabilityId id = new AvailabilityId(professorId, timeSlotId);
        if(!availabilityRepository.existsByIdAndInstitutionId(id, institutionId)){
            throw new ResourceNotFoundException("Availability not found for professorId=" + professorId + " and timeSlotId=" + timeSlotId);
        }
        availabilityRepository.deleteById(id);
    }
}
