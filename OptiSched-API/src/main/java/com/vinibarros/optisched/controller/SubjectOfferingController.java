package com.vinibarros.optisched.controller;

import com.vinibarros.optisched.dto.request.SubjectOfferingRequest;
import com.vinibarros.optisched.dto.response.SubjectOfferingResponse;
import com.vinibarros.optisched.service.SubjectOfferingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subject-offerings")
public class SubjectOfferingController {

    private final SubjectOfferingService subjectOfferingService;

    public SubjectOfferingController(SubjectOfferingService subjectOfferingService){
        this.subjectOfferingService = subjectOfferingService;
    }

    @PostMapping
    public ResponseEntity<SubjectOfferingResponse> create(@Valid @RequestBody SubjectOfferingRequest request){
        SubjectOfferingResponse response = subjectOfferingService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubjectOfferingResponse> findById(@PathVariable Long id){
        return ResponseEntity.ok(subjectOfferingService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<SubjectOfferingResponse>> findAll(
            @RequestParam(required = false) Long courseId,
            @RequestParam(required = false) Long subjectId,
            @RequestParam(required = false) Long semesterId
    ) {
        if (courseId != null) return ResponseEntity.ok(subjectOfferingService.findByCourse(courseId));
        if (subjectId != null) return ResponseEntity.ok(subjectOfferingService.findBySubject(subjectId));
        if (semesterId != null) return ResponseEntity.ok(subjectOfferingService.findBySemester(semesterId));
        return ResponseEntity.ok(subjectOfferingService.findAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<SubjectOfferingResponse> update(@PathVariable Long id, @Valid @RequestBody SubjectOfferingRequest request){
        return ResponseEntity.ok(subjectOfferingService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        subjectOfferingService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
