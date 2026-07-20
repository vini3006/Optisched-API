package com.vinibarros.optisched.controller;

import com.vinibarros.optisched.dto.response.ScheduleEntryResponse;
import com.vinibarros.optisched.service.ScheduleEntryService;
import com.vinibarros.optisched.util.MultiTenantUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.List;

@RestController
@RequestMapping("/schedule-entries")
public class ScheduleEntryController {

    private final ScheduleEntryService scheduleEntryService;

    public ScheduleEntryController(ScheduleEntryService scheduleEntryService) {
        this.scheduleEntryService = scheduleEntryService;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN', 'PROFESSOR')")
    public ResponseEntity<ScheduleEntryResponse> findById(
            @PathVariable Long id,
            @RequestParam(required = false) Long institutionIdSuperAdmin,
            @RequestAttribute(required = false) Long institutionIdAdmin,
            @RequestAttribute(required = false) Long institutionIdProfessor) {

        Long targetInstitutionId = MultiTenantUtils.resolveInstitutionId(
                "fetch schedule entry details",
                institutionIdAdmin,
                institutionIdProfessor,
                institutionIdSuperAdmin
        );

        return ResponseEntity.ok(scheduleEntryService.findById(id, targetInstitutionId));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN', 'PROFESSOR')")
    public ResponseEntity<List<ScheduleEntryResponse>> find(
            @RequestParam Long scheduleId,
            @RequestParam(required = false) Long professorId,
            @RequestParam(required = false) Long classroomId,
            @RequestParam(required = false) DayOfWeek dayOfWeek,
            @RequestParam(required = false) Long institutionIdSuperAdmin,
            @RequestAttribute(required = false) Long institutionIdAdmin,
            @RequestAttribute(required = false) Long institutionIdProfessor,
            @RequestAttribute(required = false) Long authenticatedProfessorId) {

        Long targetInstitutionId = MultiTenantUtils.resolveInstitutionId(
                "list schedule entries",
                institutionIdAdmin,
                institutionIdProfessor,
                institutionIdSuperAdmin
        );

        // Se for PROFESSOR, trava a consulta para ver apenas os horários dele
        if (authenticatedProfessorId != null) {
            if (professorId != null && !professorId.equals(authenticatedProfessorId)) {
                throw new AccessDeniedException("Professors can only view their own schedule entries.");
            }
            professorId = authenticatedProfessorId;
        }

        if (professorId != null) {
            return ResponseEntity.ok(scheduleEntryService.findByScheduleAndProfessor(scheduleId, professorId, targetInstitutionId));
        }
        if (classroomId != null) {
            return ResponseEntity.ok(scheduleEntryService.findByScheduleAndClassroom(scheduleId, classroomId, targetInstitutionId));
        }
        if (dayOfWeek != null) {
            return ResponseEntity.ok(scheduleEntryService.findByScheduleAndDayOfWeek(scheduleId, dayOfWeek, targetInstitutionId));
        }

        return ResponseEntity.ok(scheduleEntryService.findBySchedule(scheduleId, targetInstitutionId));
    }
}
