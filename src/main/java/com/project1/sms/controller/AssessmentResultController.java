package com.project1.sms.controller;
import com.project1.sms.Service.AssessmentResultService;
import com.project1.sms.globalResponse.GlobalResponse;
import java.util.List;

import com.project1.sms.responseDto.AssessmentResultResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/assessment-results")
@RequiredArgsConstructor
public class AssessmentResultController {

    private final AssessmentResultService assessmentResultService;


    @GetMapping("/students/{offeringId}")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<GlobalResponse<AssessmentResultResponse>> getStudentAssessmentResult(
            @PathVariable Long offeringId) {
        AssessmentResultResponse result = assessmentResultService.getStudentAssessmentResult(offeringId);
        return ResponseEntity.ok(GlobalResponse.success("Assessment result fetched successfully", result));
    }

    @PatchMapping("/{resultId}/mark")
    @PreAuthorize("hasAnyRole('TEACHER,REGISTRAR_HEAD,REGISTRAR_OFFICER')")
    public ResponseEntity<GlobalResponse<AssessmentResultResponse>> updateResult(
            @PathVariable Long resultId,
            @RequestParam Integer mark) {
        AssessmentResultResponse result = assessmentResultService.updateResult(resultId, mark);
        return ResponseEntity.ok(GlobalResponse.success("Assessment result mark updated successfully", result));
    }

    @GetMapping("/grade-sheets/{courseOfferingId}")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<GlobalResponse<List<AssessmentResultResponse>>> getGradeSheet(@PathVariable Long courseOfferingId) {
        List<AssessmentResultResponse> gradeSheet = assessmentResultService.getGradeSheet(courseOfferingId);
        return ResponseEntity.ok(GlobalResponse.success("Grade sheet fetched successfully", gradeSheet));
    }

    @PatchMapping("/grades/{gradeId}")
    @PreAuthorize("hasAnyRole('TEACHER,REGISTRAR_HEAD')")
    public ResponseEntity<GlobalResponse<AssessmentResultResponse>> gradeUpdate(
            @PathVariable Long gradeId,
            @RequestParam String grade) {
        AssessmentResultResponse result = assessmentResultService.gradeUpdate(gradeId, grade);
        return ResponseEntity.ok(GlobalResponse.success("Grade updated successfully", result));
    }

    @GetMapping("/grade-sheets/{courseOfferingId}")
    @PreAuthorize("hasRole('DEPARTMENT_HEAD')")
    public ResponseEntity<GlobalResponse<List<AssessmentResultResponse>>> getGradeSheetAfterSubmitted(@PathVariable Long courseOfferingId) {
        List<AssessmentResultResponse> gradeSheet = assessmentResultService.getGradeSheetAfterSubmitted(courseOfferingId);
        return ResponseEntity.ok(GlobalResponse.success("Grade sheet fetched successfully", gradeSheet));
    }
    @GetMapping("/grade-sheets/{courseOfferingId}")
    @PreAuthorize("hasAnyRole('REGISTRAR_HEAD,REGISTRAR_OFFICER')")
    public ResponseEntity<GlobalResponse<List<AssessmentResultResponse>>> getGradeSheetAfterApproved(@PathVariable Long courseOfferingId) {
        List<AssessmentResultResponse> gradeSheet = assessmentResultService.getGradeSheetAfterApproved(courseOfferingId);
        return ResponseEntity.ok(GlobalResponse.success("Grade sheet fetched successfully", gradeSheet));
    }
}
