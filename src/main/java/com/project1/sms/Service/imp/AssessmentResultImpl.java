package com.project1.sms.Service.imp;

import com.project1.sms.ResponseDto.AssessmentResultResponse;
import com.project1.sms.Service.AssessmentResultService;
import com.project1.sms.apiException.ApiException;
import com.project1.sms.dto.AssessmentResultDetailDTO;
import com.project1.sms.model.CourseOffering;
import com.project1.sms.model.AssessmentResult;
import com.project1.sms.model.Grade;
import com.project1.sms.model.Student;
import com.project1.sms.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@RequiredArgsConstructor
@Service
@Transactional(rollbackOn = Exception.class)
public class AssessmentResultImpl implements AssessmentResultService {
    private final AssessmentResultRepo assessmentResultRepo;
    private final CourseOfferingRepo offeringRepo;
    private final StudentRepo studentRepo;
    private final AssessmentRepo assessmentRepo;
    private final GradeRepo gradeRepo;

    //student
    @Override
    public Map<String, Object> getStudentAssessmentResult(Long offeringId) {
        return null;

    }

    //Teacher
    @Override
    public AssessmentResultResponse updateResult(Long resultId, Integer mark) {
        AssessmentResult result =assessmentResultRepo.findById(resultId).orElseThrow(() -> new ApiException("result is not found"));
           result.setMarksObtained(mark);
            assessmentResultRepo.save(result);
        Student student = result.getStudent();
        CourseOffering courseOffering = result.getAssessment().getCourseOffering();
       return calculateAssessment(courseOffering,student);
    }

     //Teacher department head and registrar
    @Override
    public List<AssessmentResultResponse> getGradeSheet(Long courseOfferingId) {
        List<AssessmentResultDetailDTO> rawData = assessmentResultRepo.findGradeDetails(courseOfferingId);
        CourseOffering offering = offeringRepo.findById(courseOfferingId).orElseThrow(() -> new ApiException("course offering is not found"));
        String stdId =rawData.get(0).getStudentId();
        Student student = studentRepo.findByUserUserId(stdId).orElseThrow(() -> new ApiException("student is not found"));
        Map<String, AssessmentResultResponse> tableMap = new LinkedHashMap<>();

        for (AssessmentResultDetailDTO detail : rawData) {
            // Group by student ID
            AssessmentResultResponse row = tableMap.computeIfAbsent(detail.getStudentId(), id -> {
                AssessmentResultResponse newRow = new AssessmentResultResponse();
                newRow.setStudentId(detail.getStudentId());
                newRow.setName(detail.getStudentName());
                return newRow;
            });

            // Map the assessment title (e.g., "mid(25)") to the mark
            row.getMarks().put(detail.getAssessmentResultId(), detail.getMarksObtained());

            // Increment total
            row.setTotal(row.getTotal() + (detail.getMarksObtained() != null ? detail.getMarksObtained() : 0));
        }

        // Apply grading logic as seen in table.jpg (e.g., 89 -> A, 51 -> C)
    tableMap.values().forEach(resultResponse -> {
       Grade grade = calculateLetterGrade(resultResponse,student,offering);
       resultResponse.setGrade(grade.getGrade());
       resultResponse.setGradeId(grade.getId());

    });

        return new ArrayList<>(tableMap.values());

    }

    //to let a Teacher  give NG|IC|IA
    @Override
    public AssessmentResultResponse gradeUpdate(Long gradeId,String Grade) {
        Grade grade =gradeRepo.findById(gradeId).orElseThrow(() -> new ApiException("grade is not found"));
        grade.setGrade(Grade);
        Grade updatedGrade =gradeRepo.save(grade);
        CourseOffering offering = updatedGrade.getOffering();
        Student student = updatedGrade.getStudent();

        return calculateAssessment(offering,student);


    }

    private Grade calculateLetterGrade(AssessmentResultResponse result,Student student,CourseOffering courseOffering) {
          Grade grade = gradeRepo.findByStudentAndOffering(student,courseOffering);
          if(grade.getGrade().equalsIgnoreCase("NG") || grade.getGrade().equalsIgnoreCase("IC")  || grade.getGrade().equalsIgnoreCase("IA"))
               return grade;
        int total = result.getTotal();
        if(total >= 95){
            return gradeRepo.save(new Grade("A+",student,courseOffering));
        }
        else if(total >= 85){
            return gradeRepo.save(new Grade("A",student,courseOffering));
        }
        else if(total >= 80){
            return gradeRepo.save(new Grade("A-",student,courseOffering));
        }
        else if(total >= 75){
            return gradeRepo.save(new Grade("B+",student,courseOffering));
        }
        else if(total >= 70){
            return gradeRepo.save(new Grade("B",student,courseOffering));
        }
        else if(total >= 65){
            return gradeRepo.save(new Grade("B-",student,courseOffering));
        }
        else if(total >= 60){
            return gradeRepo.save(new Grade("C+",student,courseOffering));
        }
        else if(total >= 50){
            return gradeRepo.save(new Grade("C",student,courseOffering));
        }
       else if(total >= 45){
            return gradeRepo.save(new Grade("C-",student,courseOffering));
        }

        else if(total >= 40){
            return gradeRepo.save(new Grade("D",student,courseOffering));
        }
        else{
            return gradeRepo.save(new Grade("F",student,courseOffering));
        }


    }

    private AssessmentResultResponse calculateAssessment(CourseOffering offering, Student student){


        List<AssessmentResult> results =assessmentResultRepo.findByStudentAndAssessmentCourseOffering(student,offering);
        int total = 0;

        AssessmentResultResponse resultResponse = new AssessmentResultResponse();
        for(AssessmentResult result1:results){
            total = result1.getMarksObtained() != null?result1.getMarksObtained()+total: total;
            resultResponse.getMarks().put(result1.getId(),result1.getMarksObtained());


        }


        resultResponse.setTotal(total);
        resultResponse.setName(results.get(0).getStudent().getUser().getFirstName()+" "+
                results.get(0).getStudent().getUser().getMidlName()
        );
        resultResponse.setStudentId(results.get(0).getStudent().getUserId());
        Grade grade =calculateLetterGrade(resultResponse,student,offering);
        resultResponse.setGrade(grade.getGrade());
        resultResponse.setGradeId(grade.getId());

        return resultResponse;

    }


}
