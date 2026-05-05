package com.project1.sms.Service;

import java.util.Map;

public interface GradeService {
    Map<String,Object> getStudentAssessmentResult(Long offeringId);
    Map<String,Object> updateGrade(Long gradeId, Integer mark);
}
