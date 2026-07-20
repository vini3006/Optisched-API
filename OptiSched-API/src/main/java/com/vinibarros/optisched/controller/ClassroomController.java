package com.vinibarros.optisched.controller;

import com.vinibarros.optisched.dto.request.ClassroomRequest;
import com.vinibarros.optisched.dto.response.ClassroomResponse;
import com.vinibarros.optisched.service.ClassroomService;
import com.vinibarros.optisched.util.MultiTenantUtils;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/classrooms")
public class ClassroomController {

    private final ClassroomService classroomService;

    public ClassroomController(ClassroomService classroomService) {
        this.classroomService = classroomService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<ClassroomResponse> create(
            @Valid @RequestBody ClassroomRequest request,
            @RequestParam(required = false) Long institutionIdSuperAdmin,
            @RequestAttribute(required = false) Long institutionIdAdmin) {

        Long targetInstitutionId = MultiTenantUtils.resolveInstitutionId("create a classroom", institutionIdAdmin, institutionIdSuperAdmin);

        ClassroomResponse response = classroomService.create(request, targetInstitutionId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<ClassroomResponse> findById(
            @PathVariable Long id,
            @RequestParam(required = false) Long institutionIdSuperAdmin,
            @RequestAttribute(required = false) Long institutionIdAdmin) {

        Long targetInstitutionId = MultiTenantUtils.resolveInstitutionId("fetch classroom details", institutionIdAdmin, institutionIdSuperAdmin);

        return ResponseEntity.ok(classroomService.findById(id, targetInstitutionId));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<List<ClassroomResponse>> findAll(
            @RequestParam(required = false) Long institutionIdSuperAdmin,
            @RequestAttribute(required = false) Long institutionIdAdmin) {

        Long targetInstitutionId = MultiTenantUtils.resolveInstitutionId("list classrooms", institutionIdAdmin, institutionIdSuperAdmin);

        return ResponseEntity.ok(classroomService.findAll(targetInstitutionId));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<ClassroomResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody ClassroomRequest request,
            @RequestParam(required = false) Long institutionIdSuperAdmin,
            @RequestAttribute(required = false) Long institutionIdAdmin) {

        Long targetInstitutionId = MultiTenantUtils.resolveInstitutionId("update a classroom", institutionIdAdmin, institutionIdSuperAdmin);

        return ResponseEntity.ok(classroomService.update(id, request, targetInstitutionId));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<Void> delete(
            @PathVariable Long id,
            @RequestParam(required = false) Long institutionIdSuperAdmin,
            @RequestAttribute(required = false) Long institutionIdAdmin) {

        Long targetInstitutionId = MultiTenantUtils.resolveInstitutionId("delete a classroom", institutionIdAdmin, institutionIdSuperAdmin);

        classroomService.delete(id, targetInstitutionId);
        return ResponseEntity.noContent().build();
    }
}