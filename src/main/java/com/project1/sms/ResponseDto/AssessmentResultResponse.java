package com.project1.sms.ResponseDto;

import lombok.Data;

import java.util.LinkedHashMap;
import java.util.Map;
@Data
public class AssessmentResultResponse {
    private String studentId;
    private String name;
    private Map<Object, Integer> marks = new LinkedHashMap<>(); // Title -> Mark
    private Integer total = 0;
    private String grade;
    private  Long gradeId;

}
