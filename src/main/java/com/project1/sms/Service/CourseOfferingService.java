package com.project1.sms.Service;

import com.project1.sms.requestDTO.CourseOfferingRequest;

import java.util.Map;

public interface CourseOfferingService  {
    Map<String,Object> createCourseOffering(CourseOfferingRequest request);

}
