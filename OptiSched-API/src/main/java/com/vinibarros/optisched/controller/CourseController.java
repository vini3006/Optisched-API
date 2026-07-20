package com.vinibarros.optisched.controller;


import com.vinibarros.optisched.dto.request.CourseRequest;
import com.vinibarros.optisched.dto.response.CourseResponse;
import com.vinibarros.optisched.service.CourseService;
import com.vinibarros.optisched.util.MultiTenantUtils;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<CourseResponse> create(
            @Valid @RequestBody CourseRequest request,
            @RequestParam(required = false) Long institutionIdSuperAdmin,
            @RequestAttribute(required = false) Long institutionIdAdmin) {

        Long targetInstitutionId = MultiTenantUtils.resolveInstitutionId("create a course", institutionIdAdmin, institutionIdSuperAdmin);

        CourseResponse response = courseService.create(request, targetInstitutionId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<CourseResponse> findById(
            @PathVariable Long id,
            @RequestParam(required = false) Long institutionIdSuperAdmin,
            @RequestAttribute(required = false) Long institutionIdAdmin) {

        Long targetInstitutionId = MultiTenantUtils.resolveInstitutionId("fetch course details", institutionIdAdmin, institutionIdSuperAdmin);

        return ResponseEntity.ok(courseService.findById(id, targetInstitutionId));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<List<CourseResponse>> findAll(
            @RequestParam(required = false) Long institutionIdSuperAdmin,
            @RequestAttribute(required = false) Long institutionIdAdmin) {

        Long targetInstitutionId = MultiTenantUtils.resolveInstitutionId("list courses", institutionIdAdmin, institutionIdSuperAdmin);

        return ResponseEntity.ok(courseService.findAll(targetInstitutionId));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<CourseResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody CourseRequest request,
            @RequestParam(required = false) Long institutionIdSuperAdmin,
            @RequestAttribute(required = false) Long institutionIdAdmin) {

        Long targetInstitutionId = MultiTenantUtils.resolveInstitutionId("update a course", institutionIdAdmin, institutionIdSuperAdmin);

        return ResponseEntity.ok(courseService.update(id, request, targetInstitutionId));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<Void> delete(
            @PathVariable Long id,
            @RequestParam(required = false) Long institutionIdSuperAdmin,
            @RequestAttribute(required = false) Long institutionIdAdmin) {

        Long targetInstitutionId = MultiTenantUtils.resolveInstitutionId("delete a course", institutionIdAdmin, institutionIdSuperAdmin);

        courseService.delete(id, targetInstitutionId);
        return ResponseEntity.noContent().build();
    }
}
