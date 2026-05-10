package com.project1.sms.Service;

import com.project1.sms.ResponseDto.AssessmentResultResponse;
import com.project1.sms.model.Grade;

import java.util.List;
import java.util.Map;

public interface AssessmentResultService {
    AssessmentResultResponse getStudentAssessmentResult(Long offeringId);
    AssessmentResultResponse updateResult(Long resultId, Integer mark);
     List<AssessmentResultResponse> getGradeSheet(Long courseOfferingId);
    AssessmentResultResponse gradeUpdate(Long gradeId, String Grade);

}
