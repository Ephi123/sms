package com.project1.sms.Service;

import com.project1.sms.ResponseDto.GradeResponse;

import java.util.List;
import java.util.Map;

public interface GradeService {
    Map<String,Object> getStudentAssessmentResult(Long offeringId);
    Map<String,Object> updateGrade(Long gradeId, Integer mark);
     List<GradeResponse> getGradeSheet(Long courseOfferingId);
}
