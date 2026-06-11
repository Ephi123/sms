package com.project1.sms.controller;

import com.project1.sms.Service.CourseService;
import com.project1.sms.globalResponse.GlobalResponse;
import com.project1.sms.requestDTO.CourseRequest;
import com.project1.sms.responseDto.CourseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class CourseController {
    private final CourseService courseService;

    @PostMapping("/departments/{depId}")
    @PreAuthorize("hasRole('DEAN')")
    public ResponseEntity<GlobalResponse<?>> createCourse(@RequestBody CourseRequest request,
                                                                           @PathVariable Long depId) {
          courseService.createCourse(depId,request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(GlobalResponse.success(HttpStatus.CREATED, "Course created", null));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('DEAN','REGISTRAR_HEAD')")
    public ResponseEntity<GlobalResponse<?>> getAllCourse() {
        List<CourseResponse> courses=courseService.getAllCourse();
        return ResponseEntity.ok(GlobalResponse.success("courses fetched successfully", courses));
    }
    @GetMapping("/departments/{depId}")
    @PreAuthorize("hasAnyRole('DEAN','REGISTRAR_HEAD')")
    public ResponseEntity<GlobalResponse<?>> getAllCourseByDepartment(@PathVariable Long depId) {
        List<CourseResponse> courses=courseService.getAllCourseByDepartment(depId);
        return ResponseEntity.ok(GlobalResponse.success("courses fetched successfully", courses));
    }



}
