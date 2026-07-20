package com.vinibarros.optisched.controller;

import com.vinibarros.optisched.dto.request.ScheduleRequest;
import com.vinibarros.optisched.dto.response.ScheduleResponse;
import com.vinibarros.optisched.service.ScheduleService;
import com.vinibarros.optisched.util.MultiTenantUtils;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<ScheduleResponse> findById(
            @PathVariable Long id,
            @RequestParam(required = false) Long institutionIdSuperAdmin,
            @RequestAttribute(required = false) Long institutionIdAdmin) {

        Long targetInstitutionId = MultiTenantUtils.resolveInstitutionId(
                "fetch schedule details",
                institutionIdAdmin,
                institutionIdSuperAdmin
        );

        return ResponseEntity.ok(scheduleService.findById(id, targetInstitutionId));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<List<ScheduleResponse>> findAll(
            @RequestParam(required = false) Long institutionIdSuperAdmin,
            @RequestAttribute(required = false) Long institutionIdAdmin) {

        Long targetInstitutionId = MultiTenantUtils.resolveInstitutionId(
                "list schedules",
                institutionIdAdmin,
                institutionIdSuperAdmin
        );

        return ResponseEntity.ok(scheduleService.findAll(targetInstitutionId));
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<ScheduleResponse> alterStatus(
            @PathVariable Long id,
            @RequestParam(required = false) Long institutionIdSuperAdmin,
            @RequestAttribute(required = false) Long institutionIdAdmin) {

        Long targetInstitutionId = MultiTenantUtils.resolveInstitutionId(
                "alter schedule status",
                institutionIdAdmin,
                institutionIdSuperAdmin
        );

        return ResponseEntity.ok(scheduleService.alterStatus(id, targetInstitutionId));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<Void> delete(
            @PathVariable Long id,
            @RequestParam(required = false) Long institutionIdSuperAdmin,
            @RequestAttribute(required = false) Long institutionIdAdmin) {

        Long targetInstitutionId = MultiTenantUtils.resolveInstitutionId(
                "delete a schedule",
                institutionIdAdmin,
                institutionIdSuperAdmin
        );

        scheduleService.delete(id, targetInstitutionId);
        return ResponseEntity.noContent().build();
    }
}
