package com.project1.sms.controller;

import com.project1.sms.Service.CourseOfferingService;
import com.project1.sms.requestDTO.CourseOfferingRequest;
import com.project1.sms.globalResponse.GlobalResponse;
import jakarta.validation.Valid;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/course-offerings")
public class CourseOfferingController {

    private final CourseOfferingService courseOfferingService;

    public CourseOfferingController(CourseOfferingService courseOfferingService) {
        this.courseOfferingService = courseOfferingService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GlobalResponse<Map<String,Object>>> createCourseOffering(
            @Valid @RequestBody CourseOfferingRequest request) {
        Map<String,Object> courseOffering = courseOfferingService.createCourseOffering(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(GlobalResponse.success(HttpStatus.CREATED, "Course offering created successfully", courseOffering));
    }
}
