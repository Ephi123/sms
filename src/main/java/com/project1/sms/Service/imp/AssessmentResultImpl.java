package com.project1.sms.Service.imp;

import com.project1.sms.enumeration.CourseStatus;
import com.project1.sms.responseDto.AssessmentResultResponse;
import com.project1.sms.Service.AssessmentResultService;
import com.project1.sms.apiException.ResourceNotFoundException;
import com.project1.sms.dto.AssessmentResultDetailDTO;
import com.project1.sms.model.*;
import com.project1.sms.repository.*;
import com.project1.sms.security.CurrentUserService;
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
    private final CurrentUserService currentUserService;
    private final GradeRepo gradeRepo;

    private final CourseAssignmentRepo courseAssignmentRepo;

    //student
    @Override
    public AssessmentResultResponse getStudentAssessmentResult(Long offeringId) {
        Long id = currentUserService.getUserId();
        Student student = studentRepo.findByUserId(id).orElseThrow(() -> new ResourceNotFoundException("student not found"));
        CourseOffering  offering=offeringRepo.findById(offeringId).orElseThrow(() -> new ResourceNotFoundException("offering not found"));
        List<AssessmentResultDetailDTO> results = assessmentResultRepo.findStudentGradeDetails(offeringId,student.getUser().getUserId());

        int total = 0;

        AssessmentResultResponse resultResponse = new AssessmentResultResponse();
        for(AssessmentResultDetailDTO result1:results){
            total = result1.getMarksObtained() != null?result1.getMarksObtained()+total: total;
            resultResponse.getMarks().put(result1.getAssessmentTitle(),result1.getMarksObtained());


        }


        resultResponse.setTotal(total);
        resultResponse.setName(results.get(0).getStudentName());
        resultResponse.setStudentId(results.get(0).getStudentId());

        CourseAssignment courseAssignment =courseAssignmentRepo.findByCourseOfferingId(offeringId).orElseThrow(() -> new ResourceNotFoundException("course Not Assigned"));
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

        AssessmentResult result =assessmentResultRepo.findById(resultId).orElseThrow(() -> new ResourceNotFoundException("result is not found"));
        CourseOffering courseOffering = result.getAssessment().getCourseOffering();
        CourseAssignment  courseAssignment =courseAssignmentRepo.findByCourseOfferingId(courseOffering.getId()).orElseThrow(() -> new ResourceNotFoundException("course not assigned yet"));
        if(courseAssignment.getCourseStatus() == CourseStatus.NOT_SUBMITTED){
            throw new ResourceNotFoundException("you can't insert student mark after Submitting a grade");
        }

        result.setMarksObtained(mark);
        assessmentResultRepo.save(result);
        Student student = result.getStudent();
        return calculateAssessment(courseOffering,student);
    }

    //Teacher department head and registrar
    @Override
    public List<AssessmentResultResponse> getGradeSheet(Long courseOfferingId) {
         CourseAssignment courseAssignment =courseAssignmentRepo.findByCourseOfferingId(courseOfferingId).orElseThrow(() -> new ResourceNotFoundException("course not Assigned yet"));
        CourseStatus courseStatus =courseAssignment.getCourseStatus();

        List<AssessmentResultDetailDTO> rawData =
                assessmentResultRepo.findGradeDetails(courseOfferingId);

        if (rawData == null || rawData.isEmpty()) {
            return Collections.emptyList();
        }
        CourseOffering offering = offeringRepo.findById(courseOfferingId).orElseThrow(() -> new ResourceNotFoundException("course offering is not found"));
        String stdId = rawData.get(0).getStudentId();
        Student student = studentRepo.findByUserUserId(stdId).orElseThrow(() -> new ResourceNotFoundException("student is not found"));
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
                    .orElseThrow(() -> new ResourceNotFoundException("student is not found"));

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
        Grade grade =gradeRepo.findById(gradeId).orElseThrow(() -> new ResourceNotFoundException("grade is not found"));
        grade.setGrade(Grade);
        Grade updatedGrade =gradeRepo.save(grade);
        CourseOffering offering = updatedGrade.getOffering();
        Student student = updatedGrade.getStudent();

        return calculateAssessment(offering,student);


    }

    @Override
    public List<AssessmentResultResponse> getGradeSheetAfterSubmitted(Long courseOfferingId) {

        CourseAssignment courseAssignment =courseAssignmentRepo.findByCourseOfferingId(courseOfferingId).orElseThrow(() -> new ResourceNotFoundException("course not Assigned yet"));
        CourseStatus courseStatus =courseAssignment.getCourseStatus();

        List<AssessmentResultDetailDTO> rawData =
                assessmentResultRepo.findGradeDetailsByStatus(courseOfferingId,CourseStatus.SUBMITTED);

        if (rawData == null || rawData.isEmpty()) {
            return Collections.emptyList();

        }
        CourseOffering offering = offeringRepo.findById(courseOfferingId).orElseThrow(() -> new ResourceNotFoundException("course offering is not found"));
        String stdId = rawData.get(0).getStudentId();
        Student student = studentRepo.findByUserUserId(stdId).orElseThrow(() -> new ResourceNotFoundException("student is not found"));
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
                    .orElseThrow(() -> new ResourceNotFoundException("student is not found"));

            Grade grade = gradeRepo.findByStudentAndOffering(std, offering);

            if (grade != null) {
                resultResponse.setGrade(grade.getGrade());
                resultResponse.setGradeId(grade.getId());
            }

        });

        return new ArrayList<>(tableMap.values());
    }

    @Override
    public List<AssessmentResultResponse> getGradeSheetAfterApproved(Long courseOfferingId) {

        CourseAssignment courseAssignment =courseAssignmentRepo.findByCourseOfferingId(courseOfferingId).orElseThrow(() -> new ResourceNotFoundException("course not Assigned yet"));
        CourseStatus courseStatus =courseAssignment.getCourseStatus();

        List<AssessmentResultDetailDTO> rawData =
                assessmentResultRepo.findGradeDetailsByStatus(courseOfferingId,CourseStatus.APPROVED);

        if (rawData == null || rawData.isEmpty()) {
            return Collections.emptyList();
        }
        CourseOffering offering = offeringRepo.findById(courseOfferingId).orElseThrow(() -> new ResourceNotFoundException("course offering is not found"));
        String stdId = rawData.get(0).getStudentId();
        Student student = studentRepo.findByUserUserId(stdId).orElseThrow(() -> new ResourceNotFoundException("student is not found"));
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
                    .orElseThrow(() -> new ResourceNotFoundException("student is not found"));

            Grade grade = gradeRepo.findByStudentAndOffering(std, offering);

            if (grade != null) {
                resultResponse.setGrade(grade.getGrade());
                resultResponse.setGradeId(grade.getId());
            }

        });

        return new ArrayList<>(tableMap.values());
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