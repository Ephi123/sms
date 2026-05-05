package com.project1.sms.Service;

import java.util.Map;

public interface CourseAssignmentService {
    Map<String,Object> assignCourse(Long offeringId, String teacherId);
}
