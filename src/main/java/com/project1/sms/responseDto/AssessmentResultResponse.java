package com.project1.sms.responseDto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.project1.sms.enumeration.CourseStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.util.LinkedHashMap;
import java.util.Map;
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class AssessmentResultResponse {
    private String studentId;
    private String name;
    private Map<Object, Integer> marks = new LinkedHashMap<>(); // Title -> Mark
    private Integer total = 0;
    private String grade;
    private  Long gradeId;
    @Enumerated(EnumType.STRING)
    private CourseStatus courseStatus;

}
