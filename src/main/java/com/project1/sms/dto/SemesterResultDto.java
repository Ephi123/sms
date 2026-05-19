package com.project1.sms.dto;

import java.math.BigDecimal;
import java.util.List;

public record SemesterResultDto(
        String studentId,
        Integer academicYear,
        Integer semester,
        BigDecimal gpa,
        BigDecimal cgpa,
        Integer semesterCreditHours,
        Integer cumulativeCreditHours,
        List<CourseGradeDto> courses) {
}
