package com.project1.sms.Service.imp;

import com.project1.sms.Service.GradeService;
import com.project1.sms.apiException.ApiException;
import com.project1.sms.model.CourseOffering;
import com.project1.sms.model.Grade;
import com.project1.sms.repository.AssessmentRepo;
import com.project1.sms.repository.CourseOfferingRepo;
import com.project1.sms.repository.GradeRepo;
import com.project1.sms.repository.StudentRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
@RequiredArgsConstructor
@Service
@Transactional(rollbackOn = Exception.class)
public class GradeImp implements GradeService {
    private final GradeRepo gradeRepo;
    private final CourseOfferingRepo offeringRepo;
   private final StudentRepo studentRepo;
   private final AssessmentRepo assessmentRepo;

    @Override
    public Map<String, Object> getStudentAssessmentResult(Long offeringId) {
CourseOffering offering = offeringRepo.findById(offeringId).orElseThrow(() -> new ApiException("course offering is not found "));

List<Grade> results = gradeRepo.findByAssessmentCourseOffering(offering);


        return Map.of("data",results);
    }

    @Override
    public Map<String, Object> updateGrade(Long gradeId, Integer mark) {

        return Map.of();
    }
}
