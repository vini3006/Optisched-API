package com.vinibarros.optisched.service;

import com.vinibarros.optisched.dto.request.TimeSlotRequest;
import com.vinibarros.optisched.dto.response.TimeSlotResponse;
import com.vinibarros.optisched.entity.Institution;
import com.vinibarros.optisched.entity.TimeSlot;
import com.vinibarros.optisched.exception.*;
import com.vinibarros.optisched.mapper.TimeSlotMapper;
import com.vinibarros.optisched.repository.InstitutionRepository;
import com.vinibarros.optisched.repository.TimeSlotRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.List;

@Service
public class TimeSlotService {

    private final TimeSlotRepository timeSlotRepository;
    private final InstitutionRepository institutionRepository;
    private final TimeSlotMapper timeSlotMapper;

    public TimeSlotService(TimeSlotRepository timeSlotRepository, InstitutionRepository institutionRepository, TimeSlotMapper timeSlotMapper){
        this.timeSlotRepository = timeSlotRepository;
        this.institutionRepository = institutionRepository;
        this.timeSlotMapper = timeSlotMapper;
    }

    @Transactional
    public TimeSlotResponse create(TimeSlotRequest request, Long institutionId){
        if(!request.endTime().isAfter(request.startTime())){
            throw new InvalidTimeSlotException();
        }

        if(timeSlotRepository.existsByDayOfWeekAndStartTimeAndEndTimeAndInstitutionId(request.dayOfWeek(), request.startTime(), request.endTime(), institutionId)){
            throw new DuplicateResourceException("TimeSlot already exists for day of week: " + request.dayOfWeek() + ", startTime: " + request.startTime() + " and EndTime: " + request.endTime());
        }

        if(timeSlotRepository.existsOverlappingTimeSlot(request.startTime(), request.endTime(), institutionId)){
            throw new OverlappingTimeSlotException("TimeSlot overlaps with an existing time range: " + request.startTime() + "-" + request.endTime());
        }

        Institution institution = institutionRepository.findById(institutionId)
                .orElseThrow(() -> new ResourceNotFoundException("Institution", institutionId));

        TimeSlot timeSlot = timeSlotMapper.toEntity(request, institution);
        TimeSlot saved = timeSlotRepository.save(timeSlot);
        return timeSlotMapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public TimeSlotResponse findById(Long id, Long institutionId){
        TimeSlot timeSlot = timeSlotRepository.findByIdAndInstitutionId(id, institutionId)
                .orElseThrow(() -> new ResourceNotFoundException("TimeSlot", id));
        return timeSlotMapper.toResponse(timeSlot);
    }

    @Transactional(readOnly = true)
    public List<TimeSlotResponse> findAll(Long institutionId){
        return timeSlotRepository.findAllByInstitutionId(institutionId)
                .stream()
                .map(timeSlotMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<TimeSlotResponse> findByDayOfWeek(DayOfWeek dayOfWeek, Long institutionId){
        return timeSlotRepository.findByDayOfWeekAndInstitutionId(dayOfWeek, institutionId)
                .stream()
                .map(timeSlotMapper::toResponse)
                .toList();
    }

    @Transactional
    public void delete(Long id, Long institutionId){
        if(!timeSlotRepository.existsByIdAndInstitutionId(id, institutionId)){
            throw new ResourceNotFoundException("TimeSlot", id);
        }
        try{
            timeSlotRepository.deleteById(id);
        } catch (DataIntegrityViolationException e){
            throw new ResourceInUseException("TimeSlot cannot be deleted because it is referenced by existing Availability or ScheduleEntry records");
        }
    }
}
