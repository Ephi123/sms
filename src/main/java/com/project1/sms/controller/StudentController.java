package com.project1.sms.controller;

import com.project1.sms.Service.StudentService;
import com.project1.sms.requestDTO.StudentRequest;
import com.project1.sms.requestDTO.StudentsRequest;
import com.project1.sms.globalResponse.GlobalResponse;
import com.project1.sms.responseDto.NewStudent;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('REGISTRAR_OFFICER,REGISTRAR_HEAD')")
    public ResponseEntity<GlobalResponse<?>> studentRegister(@Valid @RequestBody StudentRequest request) {
        studentService.studentRegister(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(GlobalResponse.success(HttpStatus.CREATED, "Student registered successfully", null));
    }

    @PostMapping("/with-section")
    @PreAuthorize("hasAnyRole('REGISTRAR_OFFICER,REGISTRAR_HEAD')")
    public ResponseEntity<GlobalResponse<?>> studentsRegistered(@Valid @RequestBody StudentsRequest request) {
        studentService.studentsRegistered(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(GlobalResponse.success(HttpStatus.CREATED, "Students registered successfully", null));
    }

    @GetMapping("/new")
    @PreAuthorize("hasAnyRole('FINANCE_OFFICER,REGISTRAR_OFFICER,REGISTRAR_HEAD')")
    public ResponseEntity<GlobalResponse<List<NewStudent>>> getNewStudent() {
        List<NewStudent> students = studentService.getNewStudent();
        return ResponseEntity.ok(GlobalResponse.success("New students fetched successfully", students));
    }
}
