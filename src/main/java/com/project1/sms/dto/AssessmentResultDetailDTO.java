package com.project1.sms.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssessmentResultDetailDTO {
    private String studentId;
    private String studentName;
    private Integer marksObtained;
    private Long assessmentResultId;
    private String assessmentTitle;

}
