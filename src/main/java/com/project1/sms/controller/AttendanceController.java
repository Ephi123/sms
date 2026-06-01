package com.project1.sms.controller;

import com.project1.sms.Service.AttendanceService;
import com.project1.sms.response.GlobalResponse;
import com.project1.sms.responseDto.AttendanceResponse;
import com.project1.sms.responseDto.StudentToAttendanceResponse;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {

    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @GetMapping("/offerings/{offeringId}/report")
    public ResponseEntity<GlobalResponse<List<AttendanceResponse>>> getAttendanceReport(
            @PathVariable Long offeringId) {
        List<AttendanceResponse> report = attendanceService.getAttendanceReport(offeringId);
        return ResponseEntity.ok(GlobalResponse.success("Attendance report fetched successfully", report));
    }

    @PostMapping("/offerings/{offeringId}/students/{studentId}")
    public ResponseEntity<GlobalResponse<Void>> createAttendance(
            @PathVariable Long offeringId,
            @PathVariable String studentId,
            @RequestParam int attendanceStatus) {
        attendanceService.createAttendance(offeringId, studentId, attendanceStatus);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(GlobalResponse.<Void>success(HttpStatus.CREATED, "Attendance created successfully", null));
    }

    @GetMapping("/offerings/{offeringId}/students/pending")
    public ResponseEntity<GlobalResponse<List<StudentToAttendanceResponse>>> getStudentToAttendance(
            @PathVariable Long offeringId) {
        List<StudentToAttendanceResponse> students = attendanceService.getStudentToAttendance(offeringId);
        return ResponseEntity.ok(GlobalResponse.success("Students pending attendance fetched successfully", students));
    }
}
