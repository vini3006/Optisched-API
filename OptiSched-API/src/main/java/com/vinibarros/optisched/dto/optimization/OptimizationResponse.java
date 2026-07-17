package com.vinibarros.optisched.dto.optimization;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record OptimizationResponse(
        @JsonProperty("schedule_entries")
        List<ScheduleEntryOutput> scheduleEntries
) {}
