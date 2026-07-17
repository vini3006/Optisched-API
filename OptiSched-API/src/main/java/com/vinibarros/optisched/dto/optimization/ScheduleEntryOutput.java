package com.vinibarros.optisched.dto.optimization;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ScheduleEntryOutput(
        @JsonProperty("professor_id") Long professorId,
        @JsonProperty("subject_offering_id") Long subjectOfferingId,
        @JsonProperty("classroom_id") Long classroomId,
        @JsonProperty("time_slot_id") Long timeSlotId
) {}
