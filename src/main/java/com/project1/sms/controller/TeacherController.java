package com.project1.sms.controller;

import com.project1.sms.Service.TeacherService;
import com.project1.sms.response.GlobalResponse;
import com.project1.sms.responseDto.TeacherWithDepartmentDTo;
import com.project1.sms.responseDto.UserResponse;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/teachers")
public class TeacherController {

    private final TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping("/unregistered")
    public ResponseEntity<GlobalResponse<List<UserResponse>>> getUnregisteredTeachers() {
        List<UserResponse> teachers = teacherService.getUnregisteredTeachers();
        return ResponseEntity.ok(GlobalResponse.success("Unregistered teachers fetched successfully", teachers));
    }

    @PostMapping("/users/{userId}/departments/{departmentId}")
    public ResponseEntity<GlobalResponse<?>> registerTeacher(
            @PathVariable Long userId,
            @PathVariable Long departmentId) {
        teacherService.registerTeacher(userId, departmentId);
        return ResponseEntity.ok(GlobalResponse.success("Teacher registered successfully", null));
    }

    @GetMapping("/with-departments")
    public ResponseEntity<GlobalResponse<List<TeacherWithDepartmentDTo>>> getAllTeacherWithDepartment() {
        List<TeacherWithDepartmentDTo> teachers = teacherService.getAllTeacherWithDepartment();
        return ResponseEntity.ok(GlobalResponse.success("Teachers with departments fetched successfully", teachers));
    }
}
