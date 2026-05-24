package com.project1.sms.Service;

import com.project1.sms.requestDTO.AssessmentRequest;

import java.util.Map;

public interface AssessmentService {
    Map<String,Object> createAssessment(AssessmentRequest request);
    Map<String,Object> updateAssessment(AssessmentRequest request,Long assessmentId);
    void deleteAssessment(Long assessmentId);
}
