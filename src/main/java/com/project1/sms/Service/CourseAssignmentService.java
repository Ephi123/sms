package com.project1.sms.Service;

import java.util.Map;

public interface CourseAssignmentService {
    Map<String,Object> assignCourseWithDefaultAssessment(Long offeringId, String teacherId);
}
