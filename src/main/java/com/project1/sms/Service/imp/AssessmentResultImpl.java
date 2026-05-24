package com.project1.sms.Service.imp;

import com.project1.sms.enumeration.CourseStatus;
import com.project1.sms.responseDto.AssessmentResultResponse;
import com.project1.sms.Service.AssessmentResultService;
import com.project1.sms.apiException.ApiException;
import com.project1.sms.dto.AssessmentResultDetailDTO;
import com.project1.sms.model.*;
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

    private final CourseAssignmentRepo courseAssignmentRepo;

    //student
    @Override
    public AssessmentResultResponse getStudentAssessmentResult(Long offeringId,String stdId) {
        CourseOffering  offering=offeringRepo.findById(offeringId).orElseThrow(() -> new ApiException("offering not found"));
        Student student = studentRepo.findByUserUserId(stdId).orElseThrow(() -> new ApiException("student is not found"));
        List<AssessmentResultDetailDTO> results = assessmentResultRepo.findStudentGradeDetails(offeringId,stdId);

        int total = 0;

        AssessmentResultResponse resultResponse = new AssessmentResultResponse();
        for(AssessmentResultDetailDTO result1:results){
            total = result1.getMarksObtained() != null?result1.getMarksObtained()+total: total;
            resultResponse.getMarks().put(result1.getAssessmentTitle(),result1.getMarksObtained());


        }


        resultResponse.setTotal(total);
        resultResponse.setName(results.get(0).getStudentName());
        resultResponse.setStudentId(results.get(0).getStudentId());

        CourseAssignment courseAssignment =courseAssignmentRepo.findByCourseOfferingId(offeringId).orElseThrow(() -> new ApiException("course Not Assigned"));
        if(CourseStatus.APPROVED != courseAssignment.getCourseStatus()){
            resultResponse.setGrade(null);
        }
        else{
            Grade grade = gradeRepo.findByStudentAndOffering(student,offering);

            resultResponse.setGrade(grade.getGrade());
        }

        return resultResponse;

    }

    //Teacher
    @Override
    public AssessmentResultResponse updateResult(Long resultId, Integer mark) {

        AssessmentResult result =assessmentResultRepo.findById(resultId).orElseThrow(() -> new ApiException("result is not found"));
        CourseOffering courseOffering = result.getAssessment().getCourseOffering();
        CourseAssignment  courseAssignment =courseAssignmentRepo.findByCourseOfferingId(courseOffering.getId()).orElseThrow(() -> new ApiException("course not assigned yet"));
        if(courseAssignment.getCourseStatus() == CourseStatus.NOT_SUBMITTED){
            throw new ApiException("you can't insert student mark after Submitting a grade");
        }

        result.setMarksObtained(mark);
        assessmentResultRepo.save(result);
        Student student = result.getStudent();
        return calculateAssessment(courseOffering,student);
    }

    //Teacher department head and registrar
    @Override
    public List<AssessmentResultResponse> getGradeSheet(Long courseOfferingId) {
         CourseAssignment courseAssignment =courseAssignmentRepo.findByCourseOfferingId(courseOfferingId).orElseThrow(() -> new ApiException("course not Assigned yet"));
        CourseStatus courseStatus =courseAssignment.getCourseStatus();

        List<AssessmentResultDetailDTO> rawData = assessmentResultRepo.findGradeDetails(courseOfferingId);
        CourseOffering offering = offeringRepo.findById(courseOfferingId).orElseThrow(() -> new ApiException("course offering is not found"));
        String stdId = rawData.get(0).getStudentId();
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
            row.setCourseStatus(courseStatus);
        }


        // Apply grading logic as seen in table.jpg (e.g., 89 -> A, 51 -> C)
        tableMap.values().forEach(resultResponse -> {

            Student std = studentRepo
                    .findByUserUserId(resultResponse.getStudentId())
                    .orElseThrow(() -> new ApiException("student is not found"));

            Grade grade = gradeRepo.findByStudentAndOffering(std, offering);

            if (grade != null) {
                resultResponse.setGrade(grade.getGrade());
                resultResponse.setGradeId(grade.getId());
            }

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
            grade.setGrade("A+");
            return gradeRepo.save(grade);
        }
        else if(total >= 85){
            grade.setGrade("A");
            return gradeRepo.save(grade);
        }
        else if(total >= 80){
            grade.setGrade("A-");
            return gradeRepo.save(grade);
        }
        else if(total >= 75){
            grade.setGrade("B+");
            return gradeRepo.save(grade);
        }
        else if(total >= 70){
            grade.setGrade("B");
            return gradeRepo.save(grade);
        }
        else if(total >= 65){
            grade.setGrade("B-");
            return gradeRepo.save(grade);
        }
        else if(total >= 60){
            grade.setGrade("C+");
            return gradeRepo.save(grade);
        }
        else if(total >= 50){
            grade.setGrade("C");
            return gradeRepo.save(grade);
        }
        else if(total >= 45){
            grade.setGrade("C-");
            return gradeRepo.save(grade);
        }

        else if(total >= 40){
            grade.setGrade("D");
            return gradeRepo.save(grade);
        }
        else{
            grade.setGrade("F");
            return gradeRepo.save(grade);
        }


    }

    private AssessmentResultResponse calculateAssessment(CourseOffering offering, Student student){


        List<AssessmentResult> results = assessmentResultRepo.findByStudentAndAssessmentCourseOffering(student,offering);
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
        resultResponse.setStudentId(results.get(0).getStudent().getUser().getUserId());
        Grade grade =calculateLetterGrade(resultResponse,student,offering);
        resultResponse.setGrade(grade.getGrade());
        resultResponse.setGradeId(grade.getId());

        return resultResponse;

    }


}