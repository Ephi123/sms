package com.project1.sms.Service;

import com.project1.sms.requestDTO.CourseOfferingResponse;
import com.project1.sms.responseDto.SubmittedCourseResponse;

import java.util.List;
import java.util.Map;

public interface CourseAssignmentService {
    Map<String,Object> assignCourse(Long offeringId, String teacherId);
    List<CourseOfferingResponse> getUnassignedCourses();
    List<SubmittedCourseResponse> getSubmittedCourse();
}
