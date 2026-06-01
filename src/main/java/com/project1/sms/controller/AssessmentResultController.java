package com.project1.sms.controller;
import com.project1.sms.Service.AssessmentResultService;
import com.project1.sms.response.GlobalResponse;
import java.util.List;

import com.project1.sms.responseDto.AssessmentResultResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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


    @GetMapping("/students/{studentId}/{offeringId}")
    public ResponseEntity<GlobalResponse<AssessmentResultResponse>> getStudentAssessmentResult(
            @PathVariable Long offeringId,
            @PathVariable String studentId) {
        AssessmentResultResponse result = assessmentResultService.getStudentAssessmentResult(offeringId,studentId);
        return ResponseEntity.ok(GlobalResponse.success("Assessment result fetched successfully", result));
    }

    @PatchMapping("/{resultId}/mark")
    public ResponseEntity<GlobalResponse<AssessmentResultResponse>> updateResult(
            @PathVariable Long resultId,
            @RequestParam Integer mark) {
        AssessmentResultResponse result = assessmentResultService.updateResult(resultId, mark);
        return ResponseEntity.ok(GlobalResponse.success("Assessment result mark updated successfully", result));
    }

    @GetMapping("/grade-sheets/{courseOfferingId}")
    public ResponseEntity<GlobalResponse<List<AssessmentResultResponse>>> getGradeSheet(@PathVariable Long courseOfferingId) {
        List<AssessmentResultResponse> gradeSheet = assessmentResultService.getGradeSheet(courseOfferingId);
        return ResponseEntity.ok(GlobalResponse.success("Grade sheet fetched successfully", gradeSheet));
    }

    @PatchMapping("/grades/{gradeId}")
    public ResponseEntity<GlobalResponse<AssessmentResultResponse>> gradeUpdate(
            @PathVariable Long gradeId,
            @RequestParam String grade) {
        AssessmentResultResponse result = assessmentResultService.gradeUpdate(gradeId, grade);
        return ResponseEntity.ok(GlobalResponse.success("Grade updated successfully", result));
    }
}
