package com.project1.sms.controller;

import com.project1.sms.Service.CourseAssignmentService;
import com.project1.sms.requestDTO.CourseOfferingResponse;
import com.project1.sms.response.GlobalResponse;
import java.util.List;
import java.util.Map;

import com.project1.sms.responseDto.SubmittedCourseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/course-assignments")
public class CourseAssignmentController {

    private final CourseAssignmentService courseAssignmentService;

    public CourseAssignmentController(CourseAssignmentService courseAssignmentService) {
        this.courseAssignmentService = courseAssignmentService;
    }

    @PostMapping("/offerings/{offeringId}/teachers/{teacherId}")
    public ResponseEntity<GlobalResponse<Map<String,Object>>> assignCourse(
            @PathVariable Long offeringId,
            @PathVariable String teacherId) {
        Map<String,Object> assignment = courseAssignmentService.assignCourse(offeringId, teacherId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(GlobalResponse.success(HttpStatus.CREATED, "Course assigned successfully", assignment));
    }

    @GetMapping("/unassigned")
    public ResponseEntity<GlobalResponse<List<CourseOfferingResponse>>> getUnassignedCourses() {
        List<CourseOfferingResponse>courses = courseAssignmentService.getUnassignedCourses();
        return ResponseEntity.ok(GlobalResponse.success("Unassigned courses fetched successfully", courses));
    }

    @GetMapping("/submitted")
    public ResponseEntity<GlobalResponse<List<SubmittedCourseResponse>>> getSubmittedCourse() {
        List<SubmittedCourseResponse> courses = courseAssignmentService.getSubmittedCourse();
        return ResponseEntity.ok(GlobalResponse.success("Submitted courses fetched successfully", courses));
    }
}
