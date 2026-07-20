package com.vinibarros.optisched.mapper;

import com.vinibarros.optisched.dto.request.TimeSlotRequest;
import com.vinibarros.optisched.dto.response.TimeSlotResponse;
import com.vinibarros.optisched.entity.Institution;
import com.vinibarros.optisched.entity.TimeSlot;
import org.springframework.stereotype.Component;

@Component
public class TimeSlotMapper {

    public TimeSlot toEntity(TimeSlotRequest request, Institution institution){
        TimeSlot timeSlot = new TimeSlot();

        timeSlot.setDayOfWeek(request.dayOfWeek());
        timeSlot.setStartTime(request.startTime());
        timeSlot.setEndTime(request.endTime());
        timeSlot.setInstitution(institution);
        return timeSlot;
    }

    public TimeSlotResponse toResponse(TimeSlot timeSlot){
        return new TimeSlotResponse(
                timeSlot.getId(),
                timeSlot.getDayOfWeek(),
                timeSlot.getStartTime(),
                timeSlot.getEndTime()
        );
    }
}
