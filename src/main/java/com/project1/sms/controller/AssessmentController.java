package com.project1.sms.controller;

import com.project1.sms.Service.AssessmentService;
import com.project1.sms.requestDTO.AssessmentRequest;
import com.project1.sms.response.GlobalResponse;
import jakarta.validation.Valid;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/assessments")
public class AssessmentController {

    private final AssessmentService assessmentService;

    public AssessmentController(AssessmentService assessmentService) {
        this.assessmentService = assessmentService;
    }

    @PostMapping
    public ResponseEntity<GlobalResponse<Map<String,Object>>> createAssessment(@Valid @RequestBody AssessmentRequest request) {
        Map<String,Object> assessment = assessmentService.createAssessment(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(GlobalResponse.success(HttpStatus.CREATED, "Assessment created successfully", assessment));
    }

    @PutMapping("/{assessmentId}")
    public ResponseEntity<GlobalResponse<Map<String,Object>>> updateAssessment(
            @PathVariable Long assessmentId,
            @Valid @RequestBody AssessmentRequest request) {
        Map<String,Object> assessment = assessmentService.updateAssessment(request, assessmentId);
        return ResponseEntity.ok(GlobalResponse.success("Assessment updated successfully", assessment));
    }

    @DeleteMapping("/{assessmentId}")
    public ResponseEntity<GlobalResponse<Void>> deleteAssessment(@PathVariable Long assessmentId) {
        assessmentService.deleteAssessment(assessmentId);
        return ResponseEntity.ok(GlobalResponse.<Void>success("Assessment deleted successfully", null));
    }
}
