package com.project1.sms.dto;

import java.math.BigDecimal;

public record CourseGradeDto (
    Long gradeId,
    String courseCode,
    String courseName,
    Integer creditHour,
    String letterGrade,
    BigDecimal gradePoint,
    BigDecimal qualityPoint
){}
