package com.project1.sms.Service.imp;

import com.project1.sms.Service.CourseService;
import com.project1.sms.apiException.ResourceNotFoundException;
import com.project1.sms.model.Course;
import com.project1.sms.model.Department;
import com.project1.sms.repository.CourseRepo;
import com.project1.sms.repository.DepartmentRepo;
import com.project1.sms.requestDTO.CourseRequest;
import com.project1.sms.responseDto.CourseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class CourseImpl implements CourseService {
    private final CourseRepo courseRepo;
    private final DepartmentRepo departmentRepo;
    @Override
    public void createCourse(Long depId, CourseRequest request) {
        Department department = departmentRepo.findById(depId).orElseThrow(() -> new ResourceNotFoundException("department not found"));
       Course course= Course.builder().courseName(request.courseName())
               .courseCode(request.courseCode()).
               creditHour(request.creditHour()).
               department(department).
               build();
       courseRepo.save(course);

    }

    @Override
    public List<CourseResponse> getAllCourse() {
           return courseRepo.findAll().stream()
                    .map(CourseResponse::from).toList();

    }

    @Override
    public List<CourseResponse> getAllCourseByDepartment(Long depId) {
        Department department = departmentRepo.findById(depId).orElseThrow(() -> new ResourceNotFoundException("department not found"));

      return   courseRepo.findByDepartment(department).stream().
                map(CourseResponse::from).toList();
    }
}
