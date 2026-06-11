package com.project1.sms.Service;

import com.project1.sms.requestDTO.CourseRequest;
import com.project1.sms.responseDto.CourseResponse;

import java.util.List;

public interface CourseService {
    void createCourse(Long depId, CourseRequest request);
    List<CourseResponse> getAllCourse();
    List<CourseResponse> getAllCourseByDepartment(Long depId);
}
