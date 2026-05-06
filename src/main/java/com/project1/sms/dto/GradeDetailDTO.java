package com.project1.sms.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GradeDetailDTO {
    private String studentId;
    private String studentName;
    private String assessmentTitle;
    private Integer marksObtained;

}
