package com.vinibarros.optisched.dto.optimization;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.time.LocalTime;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record TimeSlotInput(
        Long id,
        String dayOfWeek,

        @JsonFormat(pattern = "HH:mm:ss")
        LocalTime startTime,

        @JsonFormat(pattern = "HH:mm:ss")
        LocalTime endTime
)
{}
