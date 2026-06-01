package com.project1.sms.controller;

import com.project1.sms.Service.ResultService;
import com.project1.sms.dto.SemesterResultDto;
import com.project1.sms.response.GlobalResponse;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/results")
public class ResultController {

    private final ResultService resultService;

    public ResultController(ResultService resultService) {
        this.resultService = resultService;
    }

    @GetMapping("/students/{studentId}/semester")
    public ResponseEntity<GlobalResponse<SemesterResultDto>> calculateSemesterResult(
            @PathVariable String studentId,
            @RequestParam Integer academicYear,
            @RequestParam Integer semester) {
        SemesterResultDto result = resultService.calculateSemesterResult(studentId, academicYear, semester);
        return ResponseEntity.ok(GlobalResponse.success("Semester result calculated successfully", result));
    }

    @GetMapping("/students/{studentId}/semesters")
    public ResponseEntity<GlobalResponse<List<SemesterResultDto>>> calculateAllSemesterResults(@PathVariable String studentId) {
        List<SemesterResultDto> results = resultService.calculateAllSemesterResults(studentId);
        return ResponseEntity.ok(GlobalResponse.success("All semester results calculated successfully", results));
    }

    @GetMapping("/students/{studentId}/cgpa")
    public ResponseEntity<GlobalResponse<BigDecimal>> calculateCgpa(@PathVariable String studentId) {
        BigDecimal cgpa = resultService.calculateCgpa(studentId);
        return ResponseEntity.ok(GlobalResponse.success("CGPA calculated successfully", cgpa));
    }

    @PostMapping("/students/{studentId}/semester/recalculate")
    public ResponseEntity<GlobalResponse<SemesterResultDto>> recalculateAndSaveSemesterResult(
            @PathVariable String studentId,
            @RequestParam Integer academicYear,
            @RequestParam Integer semester) {
        SemesterResultDto result = resultService.recalculateAndSaveSemesterResult(studentId, academicYear, semester);
        return ResponseEntity.ok(GlobalResponse.success("Semester result recalculated successfully", result));
    }
}
