package com.project1.sms.controller;

import com.project1.sms.Service.DepartmentService;
import com.project1.sms.globalResponse.GlobalResponse;
import com.project1.sms.responseDto.DepartmentResponse;
import com.project1.sms.responseDto.DepartmentWithHeadResponse;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','REGISTRAR_HEAD','REGISTRAR_OFFICER','DEAN','VICE_DEAN')")
    public ResponseEntity<GlobalResponse<List<DepartmentResponse>>> getDepartments() {
        List<DepartmentResponse> departments = departmentService.getDepartments();
        return ResponseEntity.ok(GlobalResponse.success("Departments fetched successfully", departments));
    }

    @GetMapping("/{departmentId}")
    @PreAuthorize("hasAnyRole('ADMIN','REGISTRAR_HEAD','REGISTRAR_OFFICER','DEAN','VICE_DEAN')")
    public ResponseEntity<GlobalResponse<DepartmentResponse>> getDepartment(@PathVariable Long departmentId) {
        DepartmentResponse department = departmentService.getDepartment(departmentId);
        return ResponseEntity.ok(GlobalResponse.success("Department fetched successfully", department));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','REGISTRAR_HEAD','REGISTRAR_OFFICER','DEAN','VICE_DEAN')")
    public ResponseEntity<GlobalResponse<?>> createDepartment(@RequestParam String depName) {
        departmentService.createDepartment(depName);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(GlobalResponse.success(HttpStatus.CREATED, "Department created successfully", null));
    }

    @PutMapping("/{depId}")
    @PreAuthorize("hasAnyRole('ADMIN','REGISTRAR_HEAD','REGISTRAR_OFFICER','DEAN','VICE_DEAN')")
    public ResponseEntity<GlobalResponse<?>> updateDepartment(
            @PathVariable Long depId,
            @RequestParam String depName) {
        departmentService.updateDepartment(depId, depName);
        return ResponseEntity.ok(GlobalResponse.success("Department updated successfully", null));
    }

    @GetMapping("/without-head")
    @PreAuthorize("hasAnyRole('ADMIN','REGISTRAR_HEAD','REGISTRAR_OFFICER','DEAN','VICE_DEAN')")
    public ResponseEntity<GlobalResponse<List<DepartmentResponse>>> getDepartmentsWithNullHead() {
        List<DepartmentResponse> departments = departmentService.getDepartmentsWithNullHead();
        return ResponseEntity.ok(GlobalResponse.success("Departments without heads fetched successfully", departments));
    }

    @PatchMapping("/{departmentId}/head/{teacherId}")
    @PreAuthorize("hasAnyRole('ADMIN','REGISTRAR_HEAD','REGISTRAR_OFFICER','DEAN','VICE_DEAN')")
    public ResponseEntity<GlobalResponse<?>> setDepartmentHead(
            @PathVariable Long departmentId,
            @PathVariable Long teacherId) {
        departmentService.setDepartmentHead(departmentId, teacherId);
        return ResponseEntity.ok(GlobalResponse.success("Department head set successfully", null));
    }

    @GetMapping("/with-heads")
    @PreAuthorize("hasAnyRole('ADMIN','REGISTRAR_HEAD','REGISTRAR_OFFICER','DEAN','VICE_DEAN')")
    public ResponseEntity<GlobalResponse<List<DepartmentWithHeadResponse>>> getAllDepartmentWithHead() {
        List<DepartmentWithHeadResponse> departments = departmentService.getAllDepartmentWithHead();
        return ResponseEntity.ok(GlobalResponse.success("Departments with heads fetched successfully", departments));
    }
}
