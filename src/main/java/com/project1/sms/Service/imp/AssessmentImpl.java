package com.project1.sms.Service.imp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.project1.sms.Service.AssessmentService;
import com.project1.sms.apiException.ApiException;
import com.project1.sms.model.Assessment;
import com.project1.sms.model.CourseOffering;
import com.project1.sms.model.Enrollment;
import com.project1.sms.model.Grade;
import com.project1.sms.repository.AssessmentRepo;
import com.project1.sms.repository.CourseOfferingRepo;
import com.project1.sms.repository.EnrollRepo;
import com.project1.sms.repository.GradeRepo;
import com.project1.sms.requestDTO.AssessmentRequest;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RequiredArgsConstructor
@Transactional(rollbackOn = Exception.class)
@Service
public class AssessmentImpl implements AssessmentService {
private final CourseOfferingRepo offeringRepo;
private final AssessmentRepo assessmentRepo;
private final EnrollRepo enrollRepo;
private final GradeRepo gradeRepo;

    @Override
    public Map<String, Object> createAssessment(AssessmentRequest request) {
   CourseOffering offering = offeringRepo.findById(request.getOfferingId()).orElseThrow(() -> new ApiException("course offering not found"));
   int total = request.getWeight();
   List<Assessment> assessments =assessmentRepo.findByCourseOffering(offering);
   for(Assessment assessment: assessments){
       if(total>100){
           throw new ApiException("Total assessment load must be equals  to 100 ");
       }

              total += assessment.getWeightPercent();

               }

Assessment  newAssessment = assessmentRepo.save(new Assessment(offering,request.getTitle(),request.getWeight()));
List<Enrollment> enrollments =enrollRepo.findByCourseOffering(offering);
List<Grade> grades = new ArrayList<>();
for(Enrollment enrollment:enrollments){
    grades.add(new Grade(enrollment.getStudent(),newAssessment,0));
}
gradeRepo.saveAll(grades);

        return Map.of();

    }

    @Override
    public Map<String, Object> updateAssessment(AssessmentRequest request,Long assessmentId) {
        Assessment assessment = assessmentRepo.findById(assessmentId).orElseThrow(() -> new ApiException("Assessment is not found"));
        CourseOffering offering = offeringRepo.findById(request.getOfferingId()).orElseThrow(() -> new ApiException("course offering not found"));
        List<Assessment>  assessments = assessmentRepo.findByCourseOffering(offering);
        int total = request.getWeight();
        for(Assessment ass:assessments){
            if(Objects.equals(ass.getId(), assessmentId)){
                continue;
            }
               total += ass.getWeightPercent();
            if(total > 100){
                throw new ApiException("Total assessment load must be equals  to 100 ");
            }
        }
        assessment.setTitle(request.getTitle());
        assessment.setWeightPercent(request.getWeight());
        Assessment updatedAssessment =assessmentRepo.save(assessment);



        return Map.of("data",updatedAssessment);
    }
}
