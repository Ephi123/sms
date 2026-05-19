package com.project1.sms.Service;


import java.util.List;

public interface AssessmentResultService {
    AssessmentResultResponse updateResult(Long resultId, Integer mark);
     List<AssessmentResultResponse> getGradeSheet(Long courseOfferingId);
    AssessmentResultResponse gradeUpdate(Long gradeId, String Grade);

}
