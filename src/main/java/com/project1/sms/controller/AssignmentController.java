package com.project1.sms.controller;

import com.project1.sms.Service.AssignmentService;
import com.project1.sms.requestDTO.AssignmentUpdateRequest;
import com.project1.sms.response.GlobalResponse;
import com.project1.sms.responseDto.AssignmentResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/assignments")
public class AssignmentController {

    private final AssignmentService assignmentService;

    public AssignmentController(AssignmentService assignmentService) {
        this.assignmentService = assignmentService;
    }

    @GetMapping("/offerings/{offeringId}/active")
    public ResponseEntity<GlobalResponse<List<AssignmentResponse>>> getAssignmentBeforeDueDate(
            @PathVariable Long offeringId) {
        List<AssignmentResponse> assignments = assignmentService.getAssignmentBeforeDueDate(offeringId);
        return ResponseEntity.ok(GlobalResponse.success("Active assignments fetched successfully", assignments));
    }

    @GetMapping("/offerings/{offeringId}/submitted")
    public ResponseEntity<GlobalResponse<List<AssignmentResponse>>> getAssignmentAfterSubmission(
            @PathVariable Long offeringId) {
        List<AssignmentResponse> assignments = assignmentService.getAssignmentAfterSubmission(offeringId);
        return ResponseEntity.ok(GlobalResponse.success("Submitted assignments fetched successfully", assignments));
    }

    @PatchMapping("/{assignmentId}")
    public ResponseEntity<GlobalResponse<Void>> updateAssignmentId(
            @PathVariable Long assignmentId,
            @Valid @RequestBody AssignmentUpdateRequest request) {
        assignmentService.updateAssignmentId(assignmentId, request);
        return ResponseEntity.ok(GlobalResponse.<Void>success("Assignment updated successfully", null));
    }
}
