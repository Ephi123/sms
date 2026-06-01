package com.project1.sms.controller;

import com.project1.sms.Service.EnrollmentService;
import com.project1.sms.model.Enrollment;
import com.project1.sms.response.GlobalResponse;
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
@RequestMapping("/api/enrollments")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }



    @GetMapping("/count/by-department")
    public ResponseEntity<GlobalResponse<Integer>> getNumberOfEnrolledStudentByDepartment(
            @RequestParam int sem,
            @RequestParam String department) {
        Integer count = enrollmentService.getNumberOfEnrolledStudentByDepartment(sem, department);
        return ResponseEntity.ok(GlobalResponse.success("Department enrollment count fetched successfully", count));
    }

    @GetMapping("/count/total")
    public ResponseEntity<GlobalResponse<Integer>> getTotalNumberOfEnrolledStudents(@RequestParam int sem) {
        Integer count = enrollmentService.getTotalNumberOfEnrolledStudents(sem);
        return ResponseEntity.ok(GlobalResponse.success("Total enrollment count fetched successfully", count));
    }

    @GetMapping("/students/by-section")
    public ResponseEntity<GlobalResponse<List<Enrollment>>> getEnrolledStudentBySection(
            @RequestParam int semester,
            @RequestParam String department,
            @RequestParam int section,
            @RequestParam int studyYear) {
        List<Enrollment> students = enrollmentService.getEnrolledStudentBySection(semester, department, section, studyYear);
        return ResponseEntity.ok(GlobalResponse.success("Enrolled students fetched successfully", students));
    }

    @GetMapping("/count/by-department-and-year")
    public ResponseEntity<GlobalResponse<Integer>> getNumberOfEnrolledStudentByDepartmentAndYear(
            @RequestParam Integer sem,
            @RequestParam String department,
            @RequestParam Integer studyYear) {
        Integer count = enrollmentService.getNumberOfEnrolledStudentByDepartmentAndYear(
                sem, department, studyYear);
        return ResponseEntity.ok(GlobalResponse.success("Department and year enrollment count fetched successfully", count));
    }

    @GetMapping("/count/by-department-year-and-program")
    public ResponseEntity<GlobalResponse<Integer>> getNumberOfEnrolledStudentByDepartmentAndYearAndProgram(
            @RequestParam Integer sem,
            @RequestParam String department,
            @RequestParam Integer studyYear,
            @RequestParam String program) {
        Integer count = enrollmentService.getNumberOfEnrolledStudentByDepartmentAndYearAndProgram(
                sem, department, studyYear, program);
        return ResponseEntity.ok(GlobalResponse.success(
                "Department, year, and program enrollment count fetched successfully", count));
    }
}
