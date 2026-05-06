package com.project1.sms.Service.imp;

import com.project1.sms.ResponseDto.GradeResponse;
import com.project1.sms.Service.GradeService;
import com.project1.sms.apiException.ApiException;
import com.project1.sms.dto.GradeDetailDTO;
import com.project1.sms.model.CourseOffering;
import com.project1.sms.model.Grade;
import com.project1.sms.repository.AssessmentRepo;
import com.project1.sms.repository.CourseOfferingRepo;
import com.project1.sms.repository.GradeRepo;
import com.project1.sms.repository.StudentRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
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


        return Map.of("data", results);
    }

    @Override
    public Map<String, Object> updateGrade(Long gradeId, Integer mark) {

        return Map.of();
    }

    @Override
    public List<GradeResponse> getGradeSheet(Long courseOfferingId) {
        List<GradeDetailDTO> rawData = gradeRepo.findGradeDetails(courseOfferingId);
        Map<String, GradeResponse> tableMap = new LinkedHashMap<>();

        for (GradeDetailDTO detail : rawData) {
            // Group by student ID
            GradeResponse row = tableMap.computeIfAbsent(detail.getStudentId(), id -> {
                GradeResponse newRow = new GradeResponse();
                newRow.setStudentId(detail.getStudentId());
                newRow.setName(detail.getStudentName());
                return newRow;
            });

            // Map the assessment title (e.g., "mid(25)") to the mark
            row.getMarks().put(detail.getAssessmentTitle(), detail.getMarksObtained());

            // Increment total
            row.setTotal(row.getTotal() + (detail.getMarksObtained() != null ? detail.getMarksObtained() : 0));
        }

        // Apply grading logic as seen in table.jpg (e.g., 89 -> A, 51 -> C)
        tableMap.values().forEach(this::calculateLetterGrade);

        return new ArrayList<>(tableMap.values());

    }

    private void calculateLetterGrade(GradeResponse grade) {
        int total = grade.getTotal();
        if (total >= 90) grade.setGrade("A+");
        else if (total >= 85) grade.setGrade("A");
        else if (total >= 80) grade.setGrade("A-");
        else if (total >= 75) grade.setGrade("B+");
        else if (total >= 70) grade.setGrade("B");
        else if (total >= 65) grade.setGrade("B-");
        else if (total >= 60) grade.setGrade("c+");
        else if (total >= 50) grade.setGrade("c");
        else if (total >= 45) grade.setGrade("c-");
        else if (total >= 40) grade.setGrade("D");
        else grade.setGrade("F");
    }


}
